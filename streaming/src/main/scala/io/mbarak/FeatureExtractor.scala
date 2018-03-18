package io.mbarak

import io.mbarak.showcase.{Event, Event2, UserProfile, UserScore}
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import io.mbarak.sink.{UserFeaturesJsonSink, UserFeaturesSink, UserProfileSink}
import io.mbarak.source.SpecificRecordSource
import org.apache.flink.api.scala._
import org.apache.flink.contrib.streaming.state.{PredefinedOptions, RocksDBStateBackend}


/*
~/apps/flink-1.3.2/bin/flink run -c io.mbarak.FeatureExtractor streaming/target/scala-2.11/showcase-streaming-assembly-0.1.0-SNAPSHOT.jar -p 1
 */

object FeatureExtractor {

  def main(args: Array[String]): Unit = {
    import io.mbarak.opts.ExtractedFeaturesOpts._
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val event1Source = SpecificRecordSource("dev-v1-avro-event1", "localhost:9092", "http://localhost:8081", "feature_extraction_1" , classOf[Event])
    val event2Source = SpecificRecordSource("dev-v1-avro-event2", "localhost:9092", "http://localhost:8081", "feature_extraction_1" , classOf[Event2])
    val userScore = SpecificRecordSource("dev-v1-avro-user-scores", "localhost:9092", "http://localhost:8081", "feature_extraction_1" , classOf[UserScore])

    val eventsStream = env.addSource(event1Source)
    val events2Stream = env.addSource(event2Source)
    val userScoreStream = env.addSource(userScore)

    val userProfile: DataStream[UserProfile] = eventsStream
        .joinWithEvents2(events2Stream)
        .joinWithScores(userScoreStream)

    val features = userProfile
      .extractFeatures


    features
      .addKey
      .addSink(UserFeaturesSink("localhost:9092", "http://localhost:8081", "dev-avro-v1-user-features"))

      features
      .map(s => s.toString)
      .addSink(UserFeaturesJsonSink("localhost:9092", "dev-json-v1-user-features"))

    userProfile
        .addKey
        .addSink(UserProfileSink("localhost:9092", "http://localhost:8081", "dev-avro-v1-user-profile"))

    val backend = new RocksDBStateBackend("file:///Users/mbarak/projects/github/showcase/state", true)
    backend.setPredefinedOptions(PredefinedOptions.FLASH_SSD_OPTIMIZED)
    env.enableCheckpointing(5000)
    env.setStateBackend(backend)
    env.execute("Feature Extraction")
  }
}



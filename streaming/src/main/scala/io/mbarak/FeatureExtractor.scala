//package io.mbarak
//
//import io.mbarak.showcase.{Event, Event2, UserProfile, UserScore}
//import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
//import io.mbarak.sink.UserFeaturesSink
//import io.mbarak.source.SpecificRecordSource
//import org.apache.flink.api.scala._
//import org.apache.flink.contrib.streaming.state.{PredefinedOptions, RocksDBStateBackend}
//import org.apache.flink.runtime.state.filesystem.FsStateBackend
//import org.apache.flink.streaming.api.datastream.QueryableStateStream
//
///*
//~/apps/flink-1.4.2/bin/flink run -c io.mbarak.FeatureExtractor streaming/target/scala-2.11/showcase-streaming-assembly-0.1.0-SNAPSHOT.jar -p 1
// */
//
//object FeatureExtractor {
//
//  def main(args: Array[String]): Unit = {
//    import io.mbarak.opts.ExtractedFeaturesOpts._
//    val env = StreamExecutionEnvironment.getExecutionEnvironment
//
//    val event1Source = SpecificRecordSource("dev-avro-event1", "localhost:9092", "http://localhost:8081", "feature_extraction_1" , classOf[Event])
//    val event2Source = SpecificRecordSource("dev-avro-event2", "localhost:9092", "http://localhost:8081", "feature_extraction_1" , classOf[Event2])
//    val userScore = SpecificRecordSource("user-scores", "localhost:9092", "http://localhost:8081", "feature_extraction_1" , classOf[UserScore])
//
//    val eventsStream = env.addSource(event1Source)
//    val events2Stream = env.addSource(event2Source)
//    val userScoreStream = env.addSource(userScore)
//
//    val userProfile: DataStream[UserProfile] = eventsStream
//        .joinWithEvents2(events2Stream)
//        .joinWithScores(userScoreStream)
//
////    val userProfile: DataStream[UserProfile] = eventsStream
////      .connect(events2Stream)
////      .keyBy(_.getUserId, _.getUserId)
////      .flatMap(EventsJoin)
////      .connect(userScoreStream)
////      .keyBy(_.userId, _.getUserId)
////      .flatMap(FeaturesJoin)
//
//    userProfile
//      .extractFeatures
//      .addKey
//      .addSink(UserFeaturesSink("localhost:9092", "http://localhost:8081", "user-features"))
//
//    userProfile
//      .keyBy(_.getUserId)
//      .asQueryableState("user_profiles_query")
//
//    val backend = new RocksDBStateBackend("file:///Users/mbarak/projects/github/showcase/state", true)
//    backend.setPredefinedOptions(PredefinedOptions.FLASH_SSD_OPTIMIZED)
//    env.enableCheckpointing(5000)
//    env.setStateBackend(backend)
//    env.execute("Feature Extraction")
//  }
//}
//
//

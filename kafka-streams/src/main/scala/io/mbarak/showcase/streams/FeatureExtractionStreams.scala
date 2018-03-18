package io.mbarak.showcase.streams

import java.util.Properties
import java.util.concurrent.TimeUnit

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde
import io.mbarak.showcase._
import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.common.serialization.Serdes.StringSerde
import org.apache.kafka.common.utils.Bytes
import org.apache.kafka.streams.{KeyValue, StreamsBuilder, StreamsConfig, _}
import org.apache.kafka.streams.kstream._
import org.apache.kafka.streams.state.KeyValueStore
import com.lightbend.kafka.scala.streams.DefaultSerdes._
import com.lightbend.kafka.scala.streams.StreamsBuilderS

object FeatureExtractionStreams {

  def main(args: Array[String]): Unit = {

    val streamsConfiguration = new Properties

    streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "feature-extraction-avro")
    streamsConfiguration.put(StreamsConfig.CLIENT_ID_CONFIG, "feature-extraction-avro-client")
    streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    streamsConfiguration.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081")
    streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, classOf[SpecificAvroSerde[_ <: SpecificRecord]])
    streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, classOf[SpecificAvroSerde[_ <: SpecificRecord]])
    streamsConfiguration.put(StreamsConfig.STATE_DIR_CONFIG, "file:///Users/mbarak/projects/github/showcase/k-streams-state")
    streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, (10 * 1000).toString)

    val topology = FeatureExtractor.getFeatureExtractionStream
    val streams = new KafkaStreams(topology, streamsConfiguration)
    streams.cleanUp()
    streams.start()
  }
}

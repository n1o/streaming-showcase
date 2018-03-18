package io.mbarak.source

import java.util.Properties

import io.mbarak.serde.SpecificRecordDeserialization
import org.apache.avro.specific.SpecificRecord
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011


case object SpecificRecordSource {
  def apply[T <: SpecificRecord](topic: String, kafkaUrl: String, schemaRegistry: String, consumerGroup: String, clazz: Class[T]): FlinkKafkaConsumer011[T] = {
  val props = new Properties()

  props.setProperty("bootstrap.servers", kafkaUrl)
  props.setProperty("group.id", consumerGroup)

    new FlinkKafkaConsumer011(
    topic,
    new SpecificRecordDeserialization[T](topic, schemaRegistry, clazz),
    props
    )
  }
}
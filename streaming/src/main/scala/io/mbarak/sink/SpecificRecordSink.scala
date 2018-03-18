package io.mbarak.sink

import java.util.Properties

import cats.Eval
import io.mbarak.serde.SpecificRecordSerializer
import io.mbarak.showcase.{ExtracedFeatures, UserProfile}
import org.apache.avro.specific.SpecificRecord
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011
import org.apache.kafka.common.serialization.{Serializer, StringSerializer}

trait SpecificRecordSink[K,V <: SpecificRecord] {

  def keySerializer: Eval[Serializer[K]]
  def valueClazz: Class[V]

  def apply(kafkaUrl: String, schemaRegistry: String, topic: String): FlinkKafkaProducer011[(K,V)] = {
    val props = new Properties()

    props.setProperty("bootstrap.servers", kafkaUrl)

    val producer = new  FlinkKafkaProducer011[(K, V)](
      topic,
      new SpecificRecordSerializer[K, V](topic, schemaRegistry,valueClazz, keySerializer),
      props
    )

    producer
  }
}


case object UserFeaturesSink extends SpecificRecordSink[String, ExtracedFeatures] {
  override def keySerializer: Eval[Serializer[String]] = Eval.later((new StringSerializer).asInstanceOf[Serializer[String]])
  override def valueClazz: Class[ExtracedFeatures] = classOf[ExtracedFeatures]
}

case object UserProfileSink extends SpecificRecordSink[String, UserProfile] {
  override def keySerializer: Eval[Serializer[String]] = Eval.later((new StringSerializer).asInstanceOf[Serializer[String]])
  override def valueClazz: Class[UserProfile] = classOf[UserProfile]
}


case object UserFeaturesJsonSink {
  def apply(kafkaUrl: String, topic: String): FlinkKafkaProducer011[String] = new FlinkKafkaProducer011[String](
    kafkaUrl,
    topic,
    new SimpleStringSchema()
  )
}
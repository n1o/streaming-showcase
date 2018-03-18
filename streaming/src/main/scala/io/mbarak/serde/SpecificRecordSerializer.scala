package io.mbarak.serde

import cats.Eval
import io.confluent.kafka.serializers.{AbstractKafkaAvroSerDeConfig, KafkaAvroDeserializerConfig}
import io.mbarak.serializer.SpecificAvroSerializer
import org.apache.avro.specific.SpecificRecord
import org.apache.flink.streaming.util.serialization.KeyedSerializationSchema
import org.apache.kafka.common.serialization.Serializer

import scala.collection.JavaConverters._

class SpecificRecordSerializer[K,V <: SpecificRecord](topic: String, schemaRegistyUrl: String, valueClazz: Class[V], keySerde: Eval[Serializer[K]]) extends KeyedSerializationSchema[(K,V)] with Serializable {

  @transient lazy val customConfig = Map(
    AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG -> schemaRegistyUrl).asJava

  @transient lazy val valueSerializer = {
    val serializer = new SpecificAvroSerializer[V]()

    serializer.configure(customConfig, false)
    serializer
  }

  @transient lazy val keySerializer = {
    val serde = keySerde.value
    serde.configure(customConfig, true)
    serde
  }

  override def serializeKey(element: (K, V)) = keySerializer.serialize(topic, element._1)

  override def getTargetTopic(element: (K, V)) = topic

  override def serializeValue(element: (K, V)) = valueSerializer.serialize(topic, element._2)
}

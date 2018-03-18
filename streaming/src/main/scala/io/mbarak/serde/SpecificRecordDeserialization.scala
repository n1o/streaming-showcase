//package io.mbarak.serde
//
//import io.confluent.kafka.serializers.{AbstractKafkaAvroSerDeConfig, KafkaAvroDeserializerConfig}
//import io.mbarak.serializer.SpecificAvroDeserializer
//import org.apache.avro.specific.SpecificRecord
//import org.apache.flink.api.common.typeinfo.TypeInformation
//import org.apache.flink.api.java.typeutils.TypeExtractor
//import org.apache.flink.streaming.util.serialization.KeyedDeserializationSchema
//
//import scala.collection.JavaConverters._
//
//case class SpecificRecordDeserialization[T <: SpecificRecord](topic: String, schemaRegistryUrl: String, clazz: Class[T]) extends KeyedDeserializationSchema[T]{
//
//  @transient lazy val valueDeserializer = {
//    val deserializer = new SpecificAvroDeserializer[T]()
//
//    val customConfig = Map(
//      AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG -> schemaRegistryUrl,
//      KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG -> true
//    ).asJava
//
//    deserializer.configure(customConfig, false)
//    deserializer
//  }
//
//  override def isEndOfStream(nextElement: T): Boolean = false
//
//  override def deserialize(messageKey: Array[Byte], message: Array[Byte], topic: String, partition: Int, offset: Long): T = valueDeserializer.deserialize(topic, message)
//
//  override def getProducedType: TypeInformation[T] =
//    TypeExtractor.getForClass(clazz)
//
//}

import sbt.{ExclusionRule, _}

object Dependencies {
  lazy val testing = Seq(
    "org.scalatest" %% "scalatest" % "3.0.3" % Test,
    "org.scalacheck" %% "scalacheck" % "1.13.4" % Test
  )
  lazy val flinkDeps = {
    val flinkVersion =  "1.4.2"
    Seq(
      "org.apache.flink" %% "flink-streaming-scala" % flinkVersion % Provided,
      "org.apache.flink" %% "flink-scala" % flinkVersion % Provided,
      "org.apache.flink" %% "flink-clients" % flinkVersion % Provided ,
      "org.apache.flink" %% "flink-table" % flinkVersion % Provided,
      "org.apache.flink" %% "flink-connector-kafka-0.10" % flinkVersion,
      "org.apache.flink" %% "flink-statebackend-rocksdb" % flinkVersion,
      "org.apache.flink" %% "flink-test-utils" % flinkVersion % Test
    )
  }

  lazy val avro = Seq(
    "org.apache.avro" % "avro" % "1.8.2"
  )

  val log4j2 = Seq(
    "org.slf4j" % "slf4j-api" % "1.7.25" % Test
  )

  val kafkaAvroSerde = {
    val confluentVersion = "3.3.1"
    Seq("io.confluent" % "kafka-avro-serializer" % confluentVersion exclude ("com.fasterxml.jackson.core","jackson-databind"))
  }

  val cats = Seq(
    "org.typelevel" %% "cats-core" % "1.0.0-RC1"
  )

  lazy val kafkaClient = {
    val kafkaVersion = "0.11.0.0"
    Seq("org.apache.kafka" % "kafka-clients" % kafkaVersion)
  }
}

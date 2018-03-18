import sbt.{ExclusionRule, _}

object Dependencies {
  lazy val testing = Seq(
    "org.scalatest" %% "scalatest" % "3.0.3" % Test,
    "org.scalacheck" %% "scalacheck" % "1.13.4" % Test
  )
  lazy val (flinkDeps, flinkQueryAbleSateDeps) = {
    val flinkVersion =  "1.4.2"
    (
      Seq(
      "org.apache.flink" %% "flink-streaming-scala" % flinkVersion % Provided,
      "org.apache.flink" %% "flink-scala" % flinkVersion % Provided,
      "org.apache.flink" %% "flink-clients" % flinkVersion % Provided ,
      "org.apache.flink" %% "flink-table" % flinkVersion % Provided,
      "org.apache.flink" %% "flink-connector-kafka-0.11" % flinkVersion,
      "org.apache.flink" %% "flink-statebackend-rocksdb" % flinkVersion,
      "org.apache.flink" %% "flink-test-utils" % flinkVersion % Test,
      "org.apache.flink" % "flink-avro" % flinkVersion
    ),
      Seq(
        "org.apache.flink" %% "flink-queryable-state-client-java" % flinkVersion,
        "org.apache.flink" %% "flink-scala" % flinkVersion
      )
    )
  }

  lazy val avro = Seq(
    "org.apache.avro" % "avro" % "1.8.2"
  )

  val log4j2 = Seq(
    "org.slf4j" % "slf4j-api" % "1.7.25" % Test
  )

  val kafkaAvroSerde = {
    val confluentVersion = "4.0.0"
    Seq("io.confluent" % "kafka-avro-serializer" % confluentVersion)
  }

  val cats = Seq(
    "org.typelevel" %% "cats-core" % "1.0.0-RC1"
  )

  lazy val kafkaClient = {
    val kafkaVersion = "0.11.0.0"
    Seq("org.apache.kafka" % "kafka-clients" % kafkaVersion)
  }

  lazy val graphQlDeps = {
    val akkaHttpVersion = "10.0.11"
    Seq(
      "org.sangria-graphql" %% "sangria" % "1.4.0",
      "org.sangria-graphql" %% "sangria-spray-json" % "1.0.1",
      "org.sangria-graphql" %% "sangria-circe" % "1.2.1",
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion
    )
  }

  lazy val kafkaStreams = {
    val confluentVersion = "4.0.0"
    val kafkaVersion =  "1.0.1"
    val kafka_streams_scala_version = "0.2.0"

    Seq(
      "org.apache.kafka" % "kafka-streams" % kafkaVersion,
      "org.apache.kafka" % "kafka-clients" % kafkaVersion,
      "io.confluent" % "kafka-streams-avro-serde" % confluentVersion,
      "io.confluent" % "kafka-avro-serializer" % confluentVersion,
      "io.confluent" % "kafka-schema-registry-client" % confluentVersion,
      "com.lightbend" %% "kafka-streams-scala" % kafka_streams_scala_version
    )
  }
}

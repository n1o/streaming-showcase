import Dependencies.{flinkDeps, _}
import sbt.{Resolver}


lazy val shared = List(
  organization := "io.mbarak",
  scalaVersion :=  "2.12.4",
  version := "0.1.0-SNAPSHOT",
  name := "streaming-showcase",
  resolvers ++= Seq(
    "confluent" at "http://packages.confluent.io/maven/",
    Resolver.sonatypeRepo("public"),
    Resolver.mavenLocal
  )
)

lazy val core = (project in file("core")).
  settings(shared: _*).
  settings(
    libraryDependencies ++= avro,
    name := "showcase-core"
  )

//lazy val streaming = (project in file("streaming")).
//  dependsOn(core).
//  settings(shared: _*).
//  settings(
//    libraryDependencies ++= flinkDeps ++ kafkaAvroSerde ++ cats,
//    name := "showcase-streaming"
//  )


lazy val kStreams = (project in file("kafka-streams")).
  dependsOn(core).
  settings(shared: _*).
  settings(
    libraryDependencies ++= kafkaStreams,
    name := "showcase-kafka-streams"
  )

lazy val grqphQl = (project in file("graphql")).
  dependsOn(core).
  settings(shared: _*).
  settings(
    name := "showcase-graphql"
  )

lazy val root = (project in file(".")).aggregate(core, kStreams, grqphQl).
  settings(shared: _*).
  settings(
    name := "showcase-processing"
  )
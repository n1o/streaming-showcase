import Dependencies.{flinkDeps, _}
import sbt.{Resolver}


lazy val shared = List(
  organization := "io.mbarak",
  scalaVersion :=  "2.11.11",
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
    name := "showcase-core"
  )

lazy val streaming = (project in file("streaming")).
  settings(shared: _*).
  settings(
    libraryDependencies ++= flinkDeps,
    name := "showcase-streaming"
  )

lazy val root = (project in file(".")).aggregate(streaming, core).
  settings(shared: _*).
  settings(
    name := "showcase-processing"
  )
name := "kafka-clients"

version := "0.1"

scalaVersion := "2.12.10"

val kafkaVersion = "2.4.0"

libraryDependencies ++= Seq(
  "org.apache.kafka" %% "kafka" % kafkaVersion,
  "org.apache.kafka" % "kafka-streams" % kafkaVersion
)
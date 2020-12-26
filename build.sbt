name := "Akka Guice DI"

version := "0.1"

scalaVersion := "2.12.8"

val akkaVersion = "2.5.23"

libraryDependencies ++= Seq(
  "net.codingwell" %% "scala-guice" % "4.2.11",
  "com.google.inject.extensions" % "guice-assistedinject" % "4.2.3",
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
)

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")


mainClass := Some("akkadi.Main")
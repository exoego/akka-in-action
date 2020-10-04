name := "goticks-java"

version := "1.0"

javacOptions ++= Seq("-encoding", "UTF-8", "-Xlint:unchecked")

libraryDependencies ++= {
  val akkaVersion = "2.5.23"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "ch.qos.logback" % "logback-classic" % "1.1.3"
  )
}


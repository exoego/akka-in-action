name := "goticks-java"

version := "1.0"

javacOptions ++= Seq("-encoding", "UTF-8")

libraryDependencies ++= {
  val akkaVersion = "2.5.31"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "ch.qos.logback" % "logback-classic" % "1.1.3"
  )
}


name := "deploy"

version := "1.0"

organization := "manning"

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-Xlint",
  "-Ywarn-unused",
  "-Ywarn-dead-code",
  "-feature",
  "-language:_"
)

enablePlugins(JavaAppPackaging)

scriptClasspath +="../conf"

libraryDependencies ++= {
  val akkaVersion = "2.5.31"
  Seq(
    "com.typesafe.akka" %%  "akka-actor"      % akkaVersion,
    "com.typesafe.akka" %%  "akka-slf4j"      % akkaVersion,
    "ch.qos.logback"     %  "logback-classic" % "1.0.13",
    "com.typesafe.akka" %%  "akka-testkit"    % akkaVersion   % "test",
    "org.scalatest"     %%  "scalatest"       % "3.0.8"       % "test"
  )
}


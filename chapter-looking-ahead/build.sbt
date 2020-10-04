name := "next"

version := "1.0"

organization := "com.manning"

parallelExecution in Test := false

fork := true

libraryDependencies ++= {
  // 2.5.8 is final for akka-typed
  // 2.5.7 and 2.5.8 causes java.lang.ClassNotFoundException: akka.remote.serialization.WrappedPayloadSupport
  val akkaVersion = "2.5.6"
  Seq(
    "com.typesafe.akka"         %%  "akka-actor"              % akkaVersion,
    "com.typesafe.akka"         %%  "akka-typed"              % akkaVersion,
    "com.typesafe.akka"         %%  "akka-persistence"        % akkaVersion,
    "commons-io"                %   "commons-io"              % "2.4",
    "org.scalatest"             %%  "scalatest"               % "3.0.8"      % "test"
  )
}

name := "routing"

version := "1.0"

organization := "com.manning"

libraryDependencies ++= {
  val akkaVersion = "2.5.31"
  Seq(
    "com.typesafe.akka" %% "akka-actor"   % akkaVersion,
    "com.typesafe.akka" %% "akka-remote"  % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j"   % akkaVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion  % "test",
    "org.scalatest"     %% "scalatest"    % "3.0.8"      % "test"
  )
}

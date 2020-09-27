import com.typesafe.sbt.SbtMultiJvm
import com.typesafe.sbt.SbtMultiJvm.MultiJvmKeys.MultiJvm

val akkaVersion = "2.5.4"

val project = Project(
  id = "channels",
  base = file(".")
).settings(Defaults.coreDefaultSettings ++ SbtMultiJvm.multiJvmSettings ++ Seq(
    name := "akka-sample-multi-node-scala",
    organization := "manning",
    version := "1.0",
    libraryDependencies ++= Seq(
    "com.typesafe.akka" %%  "akka-actor"              % akkaVersion,
    "com.typesafe.akka" %%  "akka-slf4j"              % akkaVersion,
    "com.typesafe.akka" %%  "akka-remote"             % akkaVersion,
    "com.typesafe.akka" %%  "akka-multi-node-testkit" % akkaVersion,
    "com.typesafe.akka" %%  "akka-contrib"            % akkaVersion,
    "com.typesafe.akka" %%  "akka-testkit"            % akkaVersion  % "test",
    "org.scalatest"     %%  "scalatest"               % "3.0.0"      % "test"
    ),
    // make sure that MultiJvm test are compiled by the default test compilation
    compile in MultiJvm := ((compile in MultiJvm) triggeredBy (compile in Test)).value,
    // disable parallel tests
    parallelExecution in Test := false,
    // make sure that MultiJvm tests are executed by the default test target,
    // and combine the results from ordinary test and multi-jvm tests
    executeTests in Test := {
      val testResults = (executeTests in Test).value
      val multiNodeResults = (executeTests in MultiJvm).value
      val overall = (testResults.overall, multiNodeResults.overall) match {
        case (_, e @ sbt.TestResult.Error) => e
        case (e @ sbt.TestResult.Error, o) => e
        case (_, failed @ sbt.TestResult.Failed) => failed
        case (failed @ sbt.TestResult.Failed, o) => failed
        case (_, _) => sbt.TestResult.Passed
      }
      Tests.Output(overall,
        testResults.events ++ multiNodeResults.events,
        testResults.summaries ++ multiNodeResults.summaries)
    }
  )
)
.enablePlugins(MultiJvmPlugin)
.configs(MultiJvm)

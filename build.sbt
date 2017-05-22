name := "scala-elastic4s-akka-http"

version := "1.0"

scalaVersion := "2.12.2"

organization := "com.demo"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

scalacOptions += "-deprecation"

// fork a new JVM for 'run' and 'test:run'
fork := true

resolvers += Resolver.bintrayRepo("hseeberger", "maven")

libraryDependencies ++= {
  val akkaHttp    = "10.0.1"
  val scalaTest   = "3.0.1"

  Seq(
    "com.typesafe.akka"           %% "akka-http-core"         % akkaHttp,
    "com.typesafe.akka"           %% "akka-http"              % akkaHttp,
    "de.heikoseeberger"           %% "akka-http-jackson"      % "1.11.0",

    "org.scalatest"               %% "scalatest"              % scalaTest % "test",

    "com.typesafe.scala-logging"  %% "scala-logging"          % "3.5.0",
    "ch.qos.logback"              %  "logback-classic"        % "1.1.7",
    "com.typesafe.akka"           %% "akka-slf4j"             % "2.4.13",

    "com.sksamuel.elastic4s"      %% "elastic4s-core"         % "5.1.5",
    "com.sksamuel.elastic4s"      %% "elastic4s-jackson"      % "5.1.5"
  )
}
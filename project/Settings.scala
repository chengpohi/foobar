import sbt._
import sbt.Keys._

object Settings {
  scalaVersion := "2.12.1"

  resolvers ++= Seq(
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  )

  val mllibDependencies = Seq(
    "org.apache.spark" %% "spark-core" % "2.1.1",
    "org.apache.spark" %% "spark-mllib" % "2.1.1",
    "org.scalaz" %% "scalaz-core" % "7.3.0-M9",
    "org.scalaz" %% "scalaz-effect" % "7.3.0-M9",
    "org.scalaz" %% "scalaz-concurrent" % "7.3.0-M9",
    "org.scalaz" %% "scalaz-iteratee" % "7.3.0-M9",
    "org.scalatest" %% "scalatest" % "3.0.3" % "test"
  )

  lazy val akkaDependencies = Seq(
    "com.typesafe" % "config" % "1.2.1",
    "com.typesafe.akka" %% "akka-slf4j" % "2.4.16",
    "com.typesafe.akka" %% "akka-http" % "10.0.1",
    "com.typesafe.akka" %% "akka-http-testkit" % "10.0.9",
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.1"
  )

  val commonDependencies = Seq(
    "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    "org.scalaz" %% "scalaz-core" % "7.3.0-M9",
    "org.scalaz" %% "scalaz-effect" % "7.3.0-M9",
    "org.scalaz" %% "scalaz-concurrent" % "7.3.0-M9",
    "org.scalaz" %% "scalaz-iteratee" % "7.3.0-M9",
    "org.scala-lang" % "scala-reflect" % "2.12.1",
    "org.scala-lang" % "scala-compiler" % "2.12.1",
    //"org.scalanlp" %% "breeze-natives" % "0.12",
    "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.5",
    "org.scalanlp" %% "breeze" % "0.13.1",
    "com.chuusai" %% "shapeless" % "2.3.2",
    "org.jsoup" % "jsoup" % "1.8.3",
    "com.github.chengpohi" %% "elasticdsl" % "0.2.3-SNAPSHOT",
    "org.scalameta" %% "scalameta" % "1.6.0",
    "org.json4s" %% "json4s-core" % "3.5.3",
    "org.apache.commons" % "commons-lang3" % "3.6",
    "org.apache.commons" % "commons-compress" % "1.14"
  )

  val commonSetting = Seq(
    version := "1.0",
    scalaVersion := "2.12.1",
    scalacOptions ++= Seq("-language:implicitConversions",
                          "-language:higherKinds",
                          "-feature",
                          "-language:postfixOps",
                          "-Xplugin-require:macroparadise"),
    initialCommands in console := "import scalaz._, Scalaz._",
    addCompilerPlugin(
      "org.scalameta" % "paradise" % "3.0.0-M7" cross CrossVersion.full),
    addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.3"),
    libraryDependencies ++= commonDependencies
  )

  val mllibSettings = Seq(
    version := "1.0",
    scalaVersion := "2.11.8",
    scalacOptions ++= Seq("-language:implicitConversions",
                          "-language:higherKinds",
                          "-feature",
                          "-language:postfixOps",
                          "-Xplugin-require:macroparadise"),
    addCompilerPlugin(
      "org.scalameta" % "paradise" % "3.0.0-M7" cross CrossVersion.full),
    libraryDependencies ++= mllibDependencies
  )

}

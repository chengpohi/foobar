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
    "org.scalatest" %% "scalatest" % "3.0.3" % "test"
  )

  val commonDependencies = Seq(
    "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    "org.scalaz" %% "scalaz-core" % "7.3.0-M9",
    "org.scalaz" %% "scalaz-effect" % "7.3.0-M9",
    "org.scalaz" %% "scalaz-concurrent" % "7.3.0-M9",
    "org.scalaz" %% "scalaz-iteratee" % "7.3.0-M9",
    "org.scala-lang" % "scala-reflect" % "2.12.1",
    //"org.scalanlp" %% "breeze-natives" % "0.12",
    "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.5",
    "org.scalanlp" %% "breeze" % "0.13.1",
    "com.chuusai" %% "shapeless" % "2.3.2",
    "org.jsoup" % "jsoup" % "1.8.3",
    "org.scalameta" %% "scalameta" % "1.6.0"
  )


  val commonSetting = Seq(
    version := "1.0",
    scalaVersion := "2.12.1",
    scalacOptions += "-feature",
    scalacOptions += "-Xplugin-require:macroparadise",
    initialCommands in console := "import scalaz._, Scalaz._",
    addCompilerPlugin(
      "org.scalameta" % "paradise" % "3.0.0-M7" cross CrossVersion.full),
    addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.3"),
    libraryDependencies ++= commonDependencies
  )


  val mllibSettings = Seq(
    version := "1.0",
    scalaVersion := "2.11.8",
    libraryDependencies ++= mllibDependencies
  )

}

name := "scala99"

version := "0.1"

scalaVersion := "2.11.8"


resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

val commonSetting = Seq(
  version := "1.0",
  scalaVersion := "2.11.8",
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
)

sbtVersion := "0.13.9"

val commonDependencies = Seq(
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "com.chuusai" %% "shapeless" % "2.3.1",
  "org.scalaz" %% "scalaz-core" % "7.2.1",
  "org.scalaz" %% "scalaz-effect" % "7.2.1",
  "org.scala-lang" % "scala-reflect" % "2.11.8",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4",
  "com.lihaoyi" %% "fastparse" % "0.3.4"
)
libraryDependencies ++= commonDependencies

lazy val macros = project.in(file("macros"))
  .settings(commonSetting: _*)
  .settings(libraryDependencies ++= commonDependencies)
lazy val parsers = project.in(file("parser"))
  .settings(commonSetting: _*)
  .settings(libraryDependencies ++= commonDependencies)

lazy val root = project.in(file("."))
  .settings(commonSetting: _*)
  .settings(libraryDependencies ++= commonDependencies)
  .aggregate(macros, parsers)
  .dependsOn(macros, parsers)


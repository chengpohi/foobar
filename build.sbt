scalaVersion := "2.12.1"

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

val myResourceDirectory =
  Option(System.getProperty("myResourceDirectory")).getOrElse("hello_world")

unmanagedResourceDirectories in Compile += baseDirectory.value / myResourceDirectory

initialCommands in console := "import scalaz._, Scalaz._"
val commonSetting = Seq(
  version := "1.0",
  scalaVersion := "2.12.1",
  scalacOptions += "-feature",
  scalacOptions += "-Xplugin-require:macroparadise",
  addCompilerPlugin(
    "org.scalameta" % "paradise" % "3.0.0-M7" cross CrossVersion.full)
)

val mllibDependencies = Seq(
  )

val commonDependencies = Seq(
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "com.chuusai" %% "shapeless" % "2.3.2",
  "org.scalaz" %% "scalaz-core" % "7.3.0-M9",
  "org.scalaz" %% "scalaz-effect" % "7.3.0-M9",
  "org.scalaz" %% "scalaz-concurrent" % "7.3.0-M9",
  "org.scala-lang" % "scala-reflect" % "2.12.1",
  //"org.scalanlp" %% "breeze-natives" % "0.12",
  "org.scalanlp" %% "breeze-viz" % "0.13",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.5",
  "org.jsoup" % "jsoup" % "1.8.3",
  "org.scalameta" %% "scalameta" % "1.6.0"
)
libraryDependencies ++= commonDependencies

/*
lazy val mllib = project.in(file("modules/mllib"))
  .settings(commonSetting: _*)
  .settings(libraryDependencies ++= commonDependencies ++ mllibDependencies)
 */

lazy val macros = project
  .in(file("modules/macros"))
  .settings(commonSetting: _*)
  .settings(libraryDependencies ++= commonDependencies)

lazy val parsers = project
  .in(file("modules/parser"))
  .settings(commonSetting: _*)
  .settings(libraryDependencies ++= commonDependencies)

lazy val app = project
  .in(file("app"))
  .settings(commonSetting: _*)
  .settings(libraryDependencies ++= commonDependencies)
  .settings(
    name := "scala99",
    version := "0.1"
  )
  .settings(
    unmanagedResourceDirectories in Compile += baseDirectory.value / myResourceDirectory)
  .aggregate(macros, parsers)
  .dependsOn(macros, parsers)

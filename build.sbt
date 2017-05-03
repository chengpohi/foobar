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
    "org.scalameta" % "paradise" % "3.0.0-M7" cross CrossVersion.full),
  addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.3")
)

val commonDependencies = Seq(
  "org.apache.spark" % "spark-core_2.11" % "2.1.1",
  "org.apache.spark" % "spark-mllib_2.11" % "2.1.1",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "org.scalaz" %% "scalaz-core" % "7.3.0-M9",
  "org.scalaz" %% "scalaz-effect" % "7.3.0-M9",
  "org.scalaz" %% "scalaz-concurrent" % "7.3.0-M9",
  "org.scalaz" %% "scalaz-iteratee" % "7.3.0-M9",
  "org.scala-lang" % "scala-reflect" % "2.12.1",
  //"org.scalanlp" %% "breeze-natives" % "0.12",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.5",
  "org.jsoup" % "jsoup" % "1.8.3",
  "org.scalameta" %% "scalameta" % "1.6.0"
)
libraryDependencies ++= commonDependencies

lazy val mllib = project.in(file("modules/mllib"))
  .settings(commonSetting: _*)
  .settings(libraryDependencies ++= commonDependencies)

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

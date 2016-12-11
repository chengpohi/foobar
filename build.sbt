scalaVersion := "2.11.8"

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

val myResourceDirectory = Option(System.getProperty("myResourceDirectory")).getOrElse("hello_world")

unmanagedResourceDirectories in Compile += baseDirectory.value / myResourceDirectory

val commonSetting = Seq(
  version := "1.0",
  scalaVersion := "2.11.8",
  scalacOptions += "-feature",
  scalacOptions += "-Xplugin-require:macroparadise",
  initialCommands in console := "import scalaz._, Scalaz._",
  addCompilerPlugin("org.scalameta" % "paradise" % "3.0.0-M5" cross CrossVersion.full)
)

val mllibDependencies = Seq(
)

val commonDependencies = Seq(
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "com.chuusai" %% "shapeless" % "2.3.1",
  "org.scalaz" %% "scalaz-core" % "7.3.0-M4",
  "org.scalaz" %% "scalaz-effect" % "7.3.0-M4",
  "org.scala-lang" % "scala-reflect" % "2.11.8",
  //"org.scalanlp" %% "breeze-natives" % "0.12",
  "org.scalanlp" %% "breeze-viz" % "0.11.2",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4",
  "org.jsoup" % "jsoup" % "1.8.3",
  "org.scalameta" %% "scalameta" % "1.3.0",
  "org.apache.spark" %% "spark-mllib" % "2.0.2",
  "org.apache.spark" %% "spark-core" % "2.0.2"
)
libraryDependencies ++= commonDependencies

lazy val mllib = project.in(file("modules/mllib"))
  .settings(commonSetting: _*)
  .settings(libraryDependencies ++= commonDependencies ++ mllibDependencies)

lazy val macros = project.in(file("modules/macros"))
  .settings(commonSetting: _*)
  .settings(libraryDependencies ++= commonDependencies)


lazy val parsers = project.in(file("modules/parser"))
  .settings(commonSetting: _*)
  .settings(libraryDependencies ++= commonDependencies)


lazy val app = project.in(file("app"))
  .settings(commonSetting: _*)
  .settings(libraryDependencies ++= commonDependencies)
  .settings(
    name := "scala99",
    version := "0.1"
  )
  .settings(unmanagedResourceDirectories in Compile += baseDirectory.value / myResourceDirectory)
  .aggregate(macros, parsers)
  .dependsOn(macros, parsers)


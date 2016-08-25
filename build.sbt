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
  initialCommands in console := "import scalaz._, Scalaz._",
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
)


val commonDependencies = Seq(
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "com.chuusai" %% "shapeless" % "2.3.1",
  "org.scalaz" %% "scalaz-core" % "7.3.0-M4",
  "org.scalaz" %% "scalaz-effect" % "7.3.0-M4",
  "org.scala-lang" % "scala-reflect" % "2.11.8",
  "org.scalanlp" %% "breeze" % "0.12",
  "org.scalanlp" %% "breeze-natives" % "0.12",
  "org.scalanlp" %% "breeze-viz" % "0.12",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4",
  "com.lihaoyi" %% "fastparse" % "0.3.4",
  "org.jsoup" % "jsoup" % "1.8.3"
)
libraryDependencies ++= commonDependencies

lazy val macros = project.in(file("macros"))
  .settings(commonSetting: _*)
  .settings(libraryDependencies ++= commonDependencies)
lazy val parsers = project.in(file("parser"))
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


import Settings._

scalaVersion := "2.12.1"
lazy val mllib = project
  .in(file("modules/mllib"))
  .settings(mllibSettings: _*)

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
  .settings(libraryDependencies ++= commonDependencies ++ akkaDependencies)
  .settings(
    name := "scala99",
    version := "0.1"
  )
  .aggregate(macros, parsers)
  .dependsOn(macros, parsers)

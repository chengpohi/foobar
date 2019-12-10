import Settings._

lazy val mllib = project
  .in(file("modules/mllib"))
  .settings(mllibSettings: _*)

lazy val bi = project
  .in(file("modules/bi"))
  .settings(biSettings: _*)

lazy val macros = project
  .in(file("modules/macros"))
  .settings(commonSetting: _*)
  .settings(libraryDependencies ++= commonDependencies)

lazy val parsers = project
  .in(file("modules/parser"))
  .settings(commonSetting: _*)
  .settings(libraryDependencies ++= commonDependencies)

lazy val benchmark = project
  .in(file("modules/benchmark"))
  .enablePlugins(GatlingPlugin)
  .settings(
    scalaSource in Gatling := sourceDirectory.value / "main" / "scala")
  .settings(inConfig(Gatling)(Defaults.testSettings): _*)
  .settings(libraryDependencies ++= gatlingDependencies)

lazy val app = project
  .in(file("app"))
  .settings(commonSetting: _*)
  .settings(
    libraryDependencies ++= commonDependencies ++ akkaDependencies ++ dbDependencies)
  .settings(
    name := "scala99",
    version := "0.1"
  )
  .aggregate(macros, parsers)
  .dependsOn(macros, parsers)

shellPrompt := { state => System.getProperty("user.name") + "> " }

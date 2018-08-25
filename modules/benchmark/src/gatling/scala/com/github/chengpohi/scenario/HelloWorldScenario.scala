package com.github.chengpohi.scenario

import io.gatling.core.Predef.{scenario, _}
import io.gatling.http.Predef.http
import scala.concurrent.duration._

object HelloWorldScenario extends Scenario {
  val scn = scenario("home")
    .exec(
      http("request 1")
        .get("/")
    )
  val simulation = scn.inject(constantUsersPerSec(100) during (10 seconds))
}

package com.github.chengpohi.scenario

import io.gatling.core.Predef.{scenario, _}
import io.gatling.http.Predef.http


object HelloWorldScenario extends Scenario {
  val scn = scenario("home")
    .exec(
      http("request 1")
        .get("/")
    )
  val simulation = scn.inject(atOnceUsers(10))
}

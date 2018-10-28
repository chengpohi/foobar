package com.github.chengpohi.simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._


class NginxSimulation extends Simulation {
  val httpConf: HttpProtocolBuilder = http
    .baseUrl("http://127.0.0.1:8080")
    .acceptHeader(
      "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .shareConnections
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val scn = scenario("index")
    .exec(
      http("request 1")
        .get("/index.html")
    )
  val simulation = scn.inject(constantUsersPerSec(2000) during (60 seconds))

  setUp(simulation).protocols(httpConf)
}

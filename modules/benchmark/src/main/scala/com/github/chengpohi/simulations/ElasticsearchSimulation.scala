package com.github.chengpohi.simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._


class ElasticsearchSimulation extends Simulation {
  val httpConf: HttpProtocolBuilder = http
    .baseUrl("http://localhost:9200")
    .acceptHeader(
      "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .shareConnections
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val scn = scenario("index")
    .exec(
      http("index document")
        .post("/test/_doc")
        .header("Content-Type", "application/json")
        .body(StringBody(
          """
             {
             "name": "hello",
             "age": 20
             }
          """))
    )
  val simulation = scn.inject(constantUsersPerSec(1000) during (30 seconds))

  setUp(simulation).protocols(httpConf)

}

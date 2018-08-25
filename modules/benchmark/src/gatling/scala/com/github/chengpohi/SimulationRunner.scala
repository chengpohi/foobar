package com.github.chengpohi

import com.github.chengpohi.scenario.{HelloWorldScenario, Scenario}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class SimulationRunner extends Simulation {
  val httpConf: HttpProtocolBuilder = http
    .baseURL("http://computer-database.gatling.io")
    .acceptHeader(
      "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader(
      "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  run(
    HelloWorldScenario
  )

  private def run(simulations: Scenario*) = {
    val ss = simulations.map(_.simulation)
    setUp(ss: _*).protocols(httpConf)
  }
}

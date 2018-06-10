package com.github.chengpohi.scenario

import io.gatling.core.structure.PopulationBuilder

trait Scenario {
  val simulation: PopulationBuilder
}

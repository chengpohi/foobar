package learn.mllib

import org.scalatest.FlatSpec

import scala.annotation.tailrec

/**
  * mllib
  * Created by chengpohi on 8/18/15.
  */
class AlgorithmTest extends FlatSpec {
  "Mean function" should "calc mean by array" in {
    val s = Array(23, 29, 20, 32, 23, 21, 33, 25)
    assert(Algorithm.mean(Array(23, 29, 20, 32, 23, 21, 33, 25)) == 25.75)
    assert(
      Algorithm.mean(Array(23.2, 29.3, 20.5, 32, 23, 21, 33, 25)) == 25.875)
  }

  "Median function" should "calc median by array" in {
    assert(Algorithm.median(Array(23, 29, 20, 32, 23, 21, 33)) == 23)
    assert(
      Algorithm.median(Array(23.2, 29.3, 20.5, 32, 23, 21, 33, 25)) == 24.1)
  }

  "Mode function" should "calc mode by array" in {
    assert(Algorithm.mode(Array(23, 29, 20, 32, 23, 21, 33)) == 23)
    assert(Algorithm.mode(Array(25, 29.3, 20.5, 32, 23, 21, 33, 25)) == 25)
  }

  "range function" should "calc range by array" in {
    assert(Algorithm.range(Array(23, 29, 20, 32, 23, 21, 33)) == 13)
    assert(
      Algorithm.midRange(Array(25, 29.3, 20.5, 32, 23, 21, 33, 25)) == 26.75)
  }

}

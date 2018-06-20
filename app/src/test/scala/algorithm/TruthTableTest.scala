package algorithm

import algorithm.TruthTable._
import org.scalatest.{FlatSpec, Matchers}

/**
  * scala99
  * Created by chengpohi on 11/1/15.
  */
class TruthTableTest extends FlatSpec with Matchers {
  it should "" in {
    table2((a: Boolean, b: Boolean) => and(a, or(a, b))) should equal(
      List((true, true, true),
           (true, false, true),
           (false, true, false),
           (false, false, false)))
    table2((a: Boolean, b: Boolean) => and(a, or(a, !b))) should equal(
      List((true, true, true),
           (true, false, true),
           (false, true, false),
           (false, false, false)))
    table3((a: Boolean, b: Boolean, c: Boolean) =>
      and(or(and(equ(and(a, or(b, c)), a), b), a), c)) should equal(
      List(
        (true, true, true, true),
        (true, true, false, false),
        (true, false, true, true),
        (true, false, false, false),
        (false, true, true, true),
        (false, true, false, false),
        (false, false, true, false),
        (false, false, false, false)
      ))
  }
}

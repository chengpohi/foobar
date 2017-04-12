package sz

import scalaz._
import Scalaz._
import scalaz.{CaseInsensitive => CI}

object CaseInsensitiveUsage extends App {
  def map(): Unit = {
    val nums: Map[CI[String], Int] = Map(CI("One") -> 1, CI("TWO") -> 2)

    assert(nums(CI("one")) == 1)
    assert(nums(CI("two")) == 2)
  }

  def set(): Unit = {
    val set: Set[CI[String]] = Set(CI("ONE"), CI("TWO"))

    assert(set(CI("one")) === true)
    assert(set(CI("two")) === true)
  }

  map()
  set()
}

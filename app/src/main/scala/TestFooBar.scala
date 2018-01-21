/**
  * scala99
  * Created by chengpohi on 9/20/16.
  */

import scalaz.Scalaz._
import scalaz._
import scalaz.syntax.EqualOps

object TestFooBar extends App {
  1 === 1

  implicitly[Equal[Int]].equal(1, 2)

  def myAssert[T](a: EqualOps[T], b: T) = {
    a === b
  }
}
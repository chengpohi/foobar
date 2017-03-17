package sz

import scalaz._
import Scalaz._

object ApplyU extends App {
  val intToString: Int => String = _.toString
  val double: Int => Int = _ * 2
  val addTwo: Int => Int = _ + 2
  Apply[Option].ap(1.some)(some(intToString)).println
  Apply[Option].map(1.some)(intToString).println
  1.some.map(intToString).println
  Apply[Option].ap(none)(some(intToString)).println
  Apply[List].ap(List(1, 2, 3))(List(double)).println

  List(1, 2, 3).map(double).println
  val add2 = ((_: Int) + (_: Int))
  val add3 = ((_: Int) + (_: Int) + (_: Int))
  val add4 = ((_: Int) + (_: Int) + (_: Int) + (_: Int))
  Apply[Option].apply2(some(1), some(2))(add2).println
  Apply[Option].apply3(some(1), some(2), some(3))(add3).println
  Apply[Option].apply4(some(1), some(2), some(3), some(4))(add4).println
  Apply[Option].apply3(some(1), none, some(3))(add3).println

  Apply[List].tuple3(List(1, 2, 3), List("a", "b"), List(())).println

  import scalaz.syntax.apply._

  val plus1: Int => Int = _ + 1
  val plus2: Int => Int = _ + 2

  (List(1, 2, 3) <*> List(plus1, plus2)).println // f: F[A => B]
  Apply[List].ap(List(1, 2, 3))(List(plus1, plus2)).println
  (some(1) |@| some(2) |@| some(3))(_ + _ + _).println
  (some(1) |@| none[Int] |@| some(3))(_ + _ + _).println
  (List(1, 2, 3) |@| List("a", "b", "c")).tupled.println

  import scalaz.{Writer, DList}
  import scalaz.syntax.writer._

  type Logged[A] = Writer[DList[String], A]

  // log a message, return no results (hence Unit)
  def log(message: String): Logged[Unit] = DList(message).tell

  // log that we are adding, and return the results of adding x and y
  def compute(x: Int, y: Int): Logged[Int] =
    log("adding " + x + " and " + y) as (x + y)

  def addAndLog(x: Int, y: Int): Logged[Int] =
    log("begin") *> compute(x, y) <* log("end")

  val (written, result) = addAndLog(1, 2).run
  written.toList.println
  result.println

  val applyVLO = Apply[Vector] compose Apply[List] compose Apply[Option]
  applyVLO
    .apply2(Vector(
              List(1.some, none[Int]),
              List(2.some, 3.some)
            ),
            Vector(
              List("a".some, "b".some, "c".some)
            ))(_.toString + _)
    .println
}

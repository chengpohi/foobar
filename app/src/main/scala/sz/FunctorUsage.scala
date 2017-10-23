package sz

import scalaz._
import std.option._
import std.list._
import std.map._
import std.anyVal._
import std.string._
import std.tuple._
import syntax.equal._
import scalaz.concurrent.Task
import syntax.functor._


object FunctorUsage extends App {
  val len: String => Int = _.length

  println(Functor[Option].map(Some("foo"))(len))
  assert(Functor[Option].map(None)(len) === None)
  println(List("1", "12", "123").map(len))
  assert(Functor[List].map(List("qwer", "adsfg"))(len) === List(4, 5))

  val lenOption: Option[String] => Option[Int] = Functor[Option].lift(len)
  assert(lenOption(Some("abcd")) === Some(4))

  assert(Functor[List].strengthL("a", List(1, 2, 3)) === List("a" -> 1, "a" -> 2, "a" -> 3))
  assert(Functor[List].strengthR(List(1, 2, 3), "a") === List(1 -> "a", 2 -> "a", 3 -> "a"))

  println(List(1, 2, 3).strengthL("a"))
  println(List(1, 2, 3).strengthR("a"))

  val source = List("a", "aa", "b", "ccccc")
  println(source.fproduct(len))

  // We can "void" a functor, which will change any F[A] into a F[Unit]
  assert(Functor[Option].void(Some(1)) === Some(()))

  // pretend this is our database
  var database = Map("abc" → 1,
    "aaa" → 2,
    "qqq" → 3)


  def del(f: String => Boolean): Task[Int] = Task.delay {
    val (count, db) = database.foldRight(0 → List.empty[(String, Int)]) {
      case ((k, _), (d, r)) if f(k) => (d + 1, r)
      case (i, (d, r)) => (d, i :: r)
    }
    database = db.toMap
    count
  }

  // This is a task which will delete two of the three items in our database,
  val delTask = del(_.startsWith("a"))

  // it hasn't run yet
  assert(database.size === 3)

  val voidTask: Task[Unit] = Functor[Task].void(delTask)

  // There is syntax for void.
  val voidTask2: Task[Unit] = delTask.void
  //voidTask2.unsafePerformSync

  // Running the task returns a Unit.
  assert(voidTask.unsafePerformSync === (()))

  // And now our database is smaller
  assert(database.size === 1)

  val listOpt = Functor[List] compose Functor[Option]
  assert(listOpt.map(List(Some(1), None, Some(3)))(_ + 1) === List(Some(2), None, Some(4)))
}

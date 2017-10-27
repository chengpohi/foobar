package sz

object DirectTypeClassUsage extends App {

  val o1: Option[Int] = Some(0)
  val o2: Option[Option[Int]] = Some(Some(0))
  val l1: List[String] = List("one")
  val l2: List[List[String]] = List(List("one"))

  direct1()
  direct2()

  // Direct use of type class for one type, Option
  def direct1(): Unit = {
    import scalaz._
    import Scalaz._

    // Import the members of the type class instance for Option.
    import std.option.optionInstance.{bind, join}

    bind(o1)(x => if (x > 0) Some(2) else None).println
    join(o2).println
  }

  // Direct use of type class for multiple types
  def direct2(): Unit = {
    import scalaz._

    // Import the type class instances for Option and List.
    import std.list.listInstance
    import std.option.{optionInstance, optionMonoid}

    optionInstance.bind(o1)(x => if (x > 0) Some(2) else None)
    optionInstance.join(o2)
    listInstance.join(l2)

    implicit object IntSemigroup extends Semigroup[Int] {
      def append(f1: Int, f2: => Int): Int = f1 + f2
    }

    println(Semigroup[Option[Int]].append(Some(1), None))
  }

}

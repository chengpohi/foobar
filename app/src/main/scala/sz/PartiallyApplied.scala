package sz

object PartiallyApplied extends App{

  val f1: (String => Int) = _.length
  val f2: (String => String => Int) = x => y => x.length + y.length

  function1()

  def function1(): Unit = {
    import scalaz._
    import std.function._
    import syntax.monad._

    // uses implicit view ToFunctorVFromBin
    println(f1.map(_ * 2).apply("123"))
    println(f2.join.apply("123"))
  }

}

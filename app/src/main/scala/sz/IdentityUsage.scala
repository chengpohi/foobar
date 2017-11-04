package sz

object IdentityUsage extends App {
  // identity
  type Id[T] = T

  import scalaz._
  import Scalaz._

  Some(1).map(identity).println
  def isSmall(i: Int): Boolean = i < 10

  val result = (1 |=> 100000).foldRight(false)(el => acc => isSmall(el) || acc)
  println(result)

  (Map("foo" -> List(1)) merge Map("foo" -> List(1), "bar" -> List(2))).println
  (Map("foo" -> 1) merge Map("foo" -> 1, "bar" -> 2)).println
  (List(1,2,3) alignSwap List(4,5)).println
}

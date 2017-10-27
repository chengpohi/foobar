package sz

object IdUsage extends App {

  import scalaz._
  import Scalaz._

  def f(a: String): Int = a.toInt

  def g(i: Int): Double = i.toDouble

  val r = "1" |> f |> g

  println(r)
}

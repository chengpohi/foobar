package sz

import scalaz._
import Scalaz._

object ScalazStyle extends App {
  val p: Boolean = true
  val res = p ? "yes" | "no"
  println(res)

  val a = Some("Hello")
  println(a | "world")
  val b = None
  println(b | "world")
}

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

  42.right[Int]
  "foo".left[String]

  case class Version(major: Int, minor: Int) {
    require(major >= 0, "major must greater than 0: %d".format(major))
  }
}

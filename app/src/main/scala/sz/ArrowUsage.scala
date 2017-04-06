package sz

import scalaz._
import Scalaz._

object ArrowUsage extends App {
  val plus1 = (_: Int) + 1
  val plus2 = (_: Int) + 2
  val rev = (_: String).reverse
  (plus1.first apply (1, "abc")).println
  (plus1.second apply ("abc", 2)).println
  (rev.second apply (1, "abc")).println
  (plus1 *** rev apply(7, "abc")).println
  (plus1 &&& plus2 apply 7).println
  (plus1.product apply (1, 2)).println
}

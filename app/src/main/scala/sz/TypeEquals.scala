package sz
import scalaz._
import Scalaz._

object TypeEquals {
  def main(args: Array[String]): Unit = {
    val m = Map("a" -> "b", "c" -> "d")
    m.get("c") == "d" //always false, Some("d") not equal to "d"
    m.get("c") === Some("d") //must type equal, otherwise the compiler will throw error
  }
}

package sz

object ShowOpsUsage extends App {

  import scalaz._
  import Scalaz._

  "hello".println
  Show[String].show("hello").println
  2.println
  Show[Int].show(2).println

  case class MyShow(name: String)

  implicit val myShowInstance: Show[MyShow] = new Show[MyShow] {
    override def shows(f: MyShow) = f.name.toUpperCase
  }

  MyShow("myshow").println
}

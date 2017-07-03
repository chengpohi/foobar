package sz

import scalaz._
import Scalaz._

case class PrintDemo(foo: String, bar: String)

object PrintUsage extends App {
  implicit def printDemoShow: Show[PrintDemo] = new Show[PrintDemo] {
    override def show(p: PrintDemo) = Cord(s"""{"foo": "${p.foo}", "bar": ${p.bar}}""")
  }
  "str".println
  PrintDemo("foo", "bar").println
}

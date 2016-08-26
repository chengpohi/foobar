import scala.io.Source

/**
  * scala99
  * Created by chengpohi on 8/6/16.
  */
object TestHello {
  def main(args: Array[String]): Unit = {
    val bounded = new FooBar
    println(bounded.testField)
    println(bounded.testMethod().testField)
  }
  class FooBar extends SelfBounded[FooBar]
}


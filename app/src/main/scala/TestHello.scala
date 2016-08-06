import scala.io.Source

/**
  * scala99
  * Created by chengpohi on 8/6/16.
  */
object TestHello {
  def main(args: Array[String]): Unit = {
    println(Source.fromURL(getClass.getResource("/test.txt")).getLines().mkString(","))
  }
}

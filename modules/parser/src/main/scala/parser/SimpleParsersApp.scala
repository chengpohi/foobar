package parser

/**
  * Default (Template) Project
  * Created by chengpohi on 3/20/16.
  */
object SimpleParsersApp extends SimpleParsers {
  def main(args: Array[String]): Unit = {
    parse(expr, "123 + 123") match {
      case Success(res, _) => println(s"123 + 123 = $res")
      case Failure(msg, _) => println(msg)
      case Error(msg, _)   => println(msg)
    }
    println("(1 + 2) + 3" + parse(expr, "(1 + 2) + 3").get)
    parse(freq, "test 12 ppp 222") match {
      case Success(matched, _) => println(matched)
      case Failure(msg, _)     => println(msg)
      case Error(msg, _)       => println(msg)
    }
  }
}

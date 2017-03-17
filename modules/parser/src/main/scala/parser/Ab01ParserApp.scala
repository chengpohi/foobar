package parser

/**
  * Default (Template) Project
  * Created by chengpohi on 3/20/16.
  */
object Ab01ParserApp extends Ab01Parser {
  def main(args: Array[String]): Unit = {
    run("ab01").get.foreach(c => println(c))
  }
}

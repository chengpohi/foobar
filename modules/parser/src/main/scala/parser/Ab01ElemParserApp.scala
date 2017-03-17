package parser

import scala.util.parsing.input.CharSequenceReader

/**
  * Default (Template) Project
  * Created by chengpohi on 3/20/16.
  */
object Ab01ElemParserApp extends Ab01ElemParser {
  def main(args: Array[String]): Unit = {
    myParser(new CharSequenceReader("ab01ab01ab01")).get.foreach(c =>
      println(c))
  }
}

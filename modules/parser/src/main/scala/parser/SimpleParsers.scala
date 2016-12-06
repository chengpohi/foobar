package parser

import scala.util.parsing.combinator.RegexParsers

/**
  * Default (Template) Project
  * Created by chengpohi on 3/20/16.
  */
class SimpleParsers extends RegexParsers {
  def word: Parser[String] = """[a-z]+""".r ^^ {
    _.toString
  }

  def number: Parser[Int] = """(0|[1-9]\d*)""".r ^^ {
    _.toInt
  }

  def freq: Parser[WordFreq] = word ~ number ^^ { case wd ~ fr => WordFreq(wd, fr) }

  val plus: Parser[String] = "+"

  //If it's expression so recursive expr parser again
  //If it's a number return a number
  val side = "(" ~> expr <~ ")" | number
  //Get the input String
  //Match from left to right
  val expr: Parser[Int] = (side ~ plus ~ side) ^^ {
    case l ~ _ ~ r => l + r
  }

}

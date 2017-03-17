package parser

import scala.util.parsing.combinator.Parsers

/**
  * scala-parser-combinator
  * Created by chengpohi on 9/30/15.
  */
trait Ab01ElemParser extends Parsers {
  type Elem = Char
  val ab01: Parser[Char] = elem('a') | elem('b') | elem('0') | elem('1')
  val myParser: Parser[List[Char]] = ab01 *
}

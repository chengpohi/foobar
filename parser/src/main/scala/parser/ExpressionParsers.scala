package parser

import scala.util.parsing.combinator.JavaTokenParsers

/**
  * Default (Template) Project
  * Created by chengpohi on 3/20/16.
  */
//JavaTokenParsers or RegexParsers
trait ExpressionParsers extends JavaTokenParsers {
  def boolean: Parser[Expression] = ("true" | "false") ^^ { s => new BooleanLiteral(s.toBoolean) }

  def string: Parser[Expression] = stringLiteral ^^ {
    s => new StringLiteral(s)
  }

  def double: Parser[Expression] = floatingPointNumber  ^^ {
    s => new NumberLiteral(s.toDouble)
  }

  def int: Parser[Expression] = wholeNumber ^^ {
    s => new NumberLiteral(s.toInt)
  }

  def literal: Parser[Expression] = boolean | string | double | int

  override def ident: Parser[String] = """[a-zA-Z_]\w*""".r

  def variable: Parser[Expression] = ident ^^ {
    s => new Variable(s)
  }

  def expression: Parser[Expression] = literal | variable

  val jlsParser: Parser[List[Expression]] = expression *
}

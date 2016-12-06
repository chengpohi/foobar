package parser

import scala.util.parsing.combinator.Parsers
import scala.util.parsing.input.CharSequenceReader

/**
 * scala-parser-combinator
 * Created by chengpohi on 9/27/15.
 */
trait Ab01Parser extends Parsers {
  type Elem = Char

  val myParser = repeat(ab01)

  def ab01 = charParser('a') or charParser('b') or charParser('0') or charParser('1')


  def charParser(expected: Char) = new Parser[Char] {
    def apply(in: Input): ParseResult[Char] = {
      val c = in.first
      if (c == expected)
        Success(c, in.rest)
      else
        Failure("Expected '" + expected + "' got '" + c + "'", in)
    }
  }

  abstract class Parser[T] extends super.Parser[T] {
    def or(right: Parser[T]): Parser[T] = {
      val left = this
      new Parser[T] {
        def apply(in: Input) =
          left(in) match {
            case s: Success[T] => s
            case _ => right(in)
          }
      }
    }
  }

  def repeat[T](charParser: Parser[T]) = new Parser[List[T]] {
    def apply(in: Input): Success[List[T]] = {
      //apply the given Parser
      charParser(in) match {
        //if that succeeded, recurse and prepend the result
        case Success(t, next) =>
          val s = apply(next)
          Success(t :: s.get, s.next)
        //if it failed, end recursion and return the empty list
        case _ => Success(Nil, in)
      }
    }
  }

  def run(s: String): Option[List[Char]] = {
    //wrap our input into a reader
    val input = new CharSequenceReader(s)
    //run the Parser on the input
    myParser(input) match {
      //if all input has been successfully consumed, return result
      //this checks if all input is gone   ↓
      case Success(list, next) if (next.atEnd) => Some(list)
      //either an error or there is still input left
      case _ => None
    }
  }
}

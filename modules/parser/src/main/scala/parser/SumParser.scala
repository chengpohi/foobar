package parser

import fastparse.WhitespaceApi

import scala.io.StdIn._

/**
  * scala-parser-combinator
  * Created by chengpohi on 12/18/15.
  */
object SumParser {

  val White = WhitespaceApi.Wrapper {
    import fastparse.all._
    NoTrace(" ".rep)
  }

  import White._
  import fastparse.noApi._

  val number: P[Int] = P(CharIn('0' to '9').rep(1)).!.map(_.toInt)
  val parens: P[Int] = P("(" ~/ addSub ~ ")")
  val sqrt: P[Int] = P("sqrt" ~ number).map(Math.sqrt(_).toInt)
  val sqrtQ: P[Int] = P("sqrt" ~ number ~/ "id").map(Math.sqrt(_).toInt)
  val factor: P[Int] = P(number | parens | sqrt | sqrtQ)

  val divMul: P[Int] = P(factor ~ (CharIn("*/").! ~/ factor).rep).map(eval)
  val addSub: P[Int] = P(divMul ~ (CharIn("+-").! ~/ divMul).rep).map(eval)

  val expr: P[Int] = P(addSub ~ End)

  def eval(tree: (Int, Seq[(String, Int)])): Int = {
    val (base, ops) = tree
    ops.foldLeft(base) {
      case (left, (op, right)) =>
        op match {
          case "+" => left + right
          case "-" => left - right
          case "*" => left * right
          case "/" => left / right
        }
    }
  }

  def main(args: Array[String]): Unit = {
    println(expr.parse("3+4").get)
    println(expr.parse("4-3").get)
    println(expr.parse("4*3").get)
    println(expr.parse("4/2").get)
    println(expr.parse("4+2*4").get)
    println(expr.parse("(4+2)*4").get)
    println(expr.parse("(4-2)*4").get)
    println(expr.parse("(4-2)*sqrt 4").get)
    println(expr.parse("(8 - 2)*sqrt 4").get)
    println(expr.parse("(8 - 2)*sqrt 4 id").get)

    while (true) {
      val str = readLine()
      if (str == "exit") System.exit(0)
      println(expr.parse(str).get)
    }
  }
}

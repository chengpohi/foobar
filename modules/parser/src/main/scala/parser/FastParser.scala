package parser

import scala.collection.mutable

/**
  * scala-parser-combinator
  * Created by chengpohi on 12/16/15.
  */
object FastParser {

  import fastparse.all._

  def eval(tree: (Int, Seq[(String, Int)])) = {
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

  val number: P[Int] = P(CharIn('0' to '9').rep(1)).!.map(_.trim.toInt)
  val parents: P[Int] = P("(" ~/ addSub ~ ")")
  val factor: P[Int] = P(number | parents)

  val divMul: P[Int] = P(factor ~ (CharIn("*/").! ~/ factor).rep).map(eval)
  val addSub: P[Int] = P(divMul ~ (CharIn("+-").! ~/ divMul).rep).map(eval)
  val expr: P[Int] = P(addSub ~ End)

  val parserA = P("a")
  val abParser = P("a" ~ "b")
  val aabParser = P("a".rep ~ "b")
  val abc = P("a".rep(sep = "b") ~ "c")
  val ab4 = P("a".rep(min = 2, max = 4, sep = "b"))
  val ab4c = P("a".rep(min = 2, max = 4, sep = "b") ~ "c")
  val option = P("c".? ~ "a".rep(sep = "b").! ~ End)
  val either = P("a".rep ~ ("b" | "c" | "d") ~ End)
  val withEnd = P("a".rep ~ "b" ~ End)
  val startParser = P((("a" | Start) ~ "b").rep ~ End)
  val finder = P("hay".rep ~ Index ~ "needle" ~ "hay".rep)

  def printEval(str: String) = {
    expr.parse(str) match {
      case Parsed.Success(value, _) => println("Eval: " + value)
      case Parsed.Failure(_, _, detail) => println("Eval: " + detail)
    }
  }

  def main(args: Array[String]): Unit = {
    printEval("3+1+2")
    printEval("3*1")
    printEval("300*1234")
    printEval("300*(1234+1)")
    printEval("3 * 1")
    println("aparer: " + parserA.parse("a"))
    println("aparser: " + parserA.parse("b"))
    println("abParser: " + abParser.parse("ab"))
    println("abParser: " + abParser.parse("aa"))
    println("aabParser: " + aabParser.parse("aab"))
    println("abcParser: " + abc.parse("abababac"))
    println("abcParser: " + abc.parse("aaaaabc"))
    println("ab4Parser: " + ab4.parse("ababab"))
    println("ab4cParser: " + ab4c.parse("ababac"))
    println("option: " + option.parse("ababa"))
    println("option: " + option.parse("cababa"))
    println("option: " + option.parse("ccababa"))
    println("either: " + either.parse("ab"))
    println("either: " + either.parse("ac"))
    println("either: " + either.parse("abc"))
    println("withEnd: " + withEnd.parse("aaab"))
    println("withEnd: " + withEnd.parse("aaaba"))
    println("start parser: " + startParser.parse("abab"))
    println("start parser: " + startParser.parse("bab"))
    println("start parser: " + startParser.parse("bbab"))
    println("finder parser: " + finder.parse("hayhayhayneedlehay"))
    val capture1 = P("a".rep.! ~ "b" ~ End)
    println(capture1.parse("aaaab"))
    val capture2 = P("a".rep.! ~ "b".! ~ End)
    println(capture2.parse("aaaab"))
    val capture3 = P("a".rep.! ~ "b".! ~ "c".! ~ End)
    println(capture3.parse("aaaabc"))
    val captureRep = P("a".!.rep ~ "b".! ~ "c".! ~ End)
    println(captureRep.parse("aaaabc"))
    val captureOpt = P("a".rep.! ~ "b".!.? ~ End)
    println(captureOpt.parse("aaaab"))

    val anyChar = P("'" ~ AnyChar.! ~ "'")
    println(anyChar.parse("'a'"))

    val keyword = P(("hello" ~ &(" ")).!.rep)
    println(keyword.parse("hello "))
    val negativeLookahead = P("hello" ~ !" " ~ AnyChar ~ "world").!
    println(negativeLookahead.parse("hello-world"))

    val binary = P(("0" | "1").rep.!)
    val binaryNum = P(binary.map(Integer.parseInt(_, 2)))
    println(binary.parse("1100"))
    println(binaryNum.parse("1100"))

    val leftTag = P("<" ~ (!">" ~ AnyChar).rep(1).! ~ ">")

    def rightTag(str: String) = P("</" ~ str.! ~ ">")

    val xml = P(leftTag.flatMap(rightTag))
    println(xml.parse("<hello></hello>"))

    val digits = P(CharIn('0' to '9').rep(1).!).map(_.toInt)
    val even = digits.filter(_ % 2 == 0)
    val odd = digits.filter(_ % 2 != 0)
    println(even.parse("124"))

    val digit = CharIn('0' to '9')
    val letter = CharIn('A' to 'Z')

    def twice[T](p: Parser[T]) = p ~ p

    def errorMessage[T](p: Parser[T], str: String) =
      ParseError(p.parse(str).asInstanceOf[Parsed.Failure]).getMessage

    val numberPlate = P(
      twice(digit) ~ "-" ~ twice(letter) ~ "-" ~ twice(digit))
    println(errorMessage(numberPlate, "11-A1-22"))
    val opaque = numberPlate.opaque("<number-plate>")
    println(errorMessage(opaque, "11-A1-22"))

    val logged = mutable.Buffer.empty[String]
    implicit val logger = fastparse.core.Logger(logged.append(_))

    val DeepFailure = P("C")
    val Foo = P((DeepFailure.log() | "A".log()) ~ "B".!.log()).log()

    Foo.parse("AB")

    val allLogged = logged.mkString("\n")
    println(allLogged)
  }
}

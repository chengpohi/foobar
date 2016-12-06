package parser

/**
 * scala-parser-combinator
 * Created by chengpohi on 12/24/15.
 */
object JsonParser {
  import fastparse.all._

  val Whitespace = NamedFunction(" \r\n".contains(_: Char), "Whitespace")
  val Digits = NamedFunction('0' to '9' contains (_: Char), "Digits")
  val StringChars = NamedFunction(!"\"\\".contains(_: Char), "StringChars")

  val space = P(CharsWhile(Whitespace).?)
  val digits = P(CharsWhile(Digits))
  val exponent = P(CharIn("eE") ~ CharIn("+-").? ~ digits)

  val fractional = P("." ~ digits)
  val integral = P("0" | CharIn('1' to '9') ~ digits.?)

  val number = P(CharIn("+-").? ~ integral ~ fractional.? ~ exponent.?).!.map(
    x => Js.Num(x.toDouble)
  )

  val `null` = P("null").map(_ => Js.Null)
  val `false` = P("false").map(_ => Js.False)
  val `true` = P("true").map(_ => Js.True)

  val hexDigit = P(CharIn('0' to '9', 'a' to 'f', 'A' to 'F'))
  val unicodeEscape = P("u" ~ hexDigit ~ hexDigit ~ hexDigit ~ hexDigit)
  val escape = P("\\" ~ (CharIn("\"/\\bfnrt") | unicodeEscape))

  val strChars = P(CharsWhile(StringChars))
  val string = P (space ~ "\"" ~/ (strChars | escape).rep.! ~ "\"").map(Js.Str)
  val array = P("[" ~ jsonExpr.rep(sep=",".~/) ~ space ~ "]").map(Js.Arr(_:_*))
  val pair = P(string.map(_.value) ~/ ":" ~/ jsonExpr)
  val obj = P("{" ~/ pair.rep(sep=",".~/) ~ space ~ "}").map(Js.Obj(_:_*))

  val jsonExpr: P[Js.Val] = P(
    space ~ (obj | array | string | `true` | `false` | `null` | number) ~ space
  )

  def main(args: Array[String]): Unit = {
    println(jsonExpr.parse(
      """{
        |"omg": "123",
        |"wtf": 12.4123,
        |"names": ["jack", "jason"],
        |"test": "中国"
        |}""".stripMargin))
  }
}

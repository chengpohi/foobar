package parser

/**
 * scala-parser-combinator
 * Created by chengpohi on 12/14/15.
 */
object Hello {

  case class Expr()

  case class Let(ident: String, bound: Expr, returned: Expr)

  import WsApi._
  import fastparse.noApi._

  val dummyExpr = P("myexpr")
  val expr: Parser[Expr] = P( dummyExpr | let ).!.map(_ => Expr())
  val let = P( "let" ~ identifier ~ "=" ~ expr ~ "in" ~ expr ~ End ).map {
    case (ident, lhs, rhs) => Let(ident, lhs, rhs)
  }
  val identifier = P(CharIn('a' to 'z', 'A' to 'Z', "_'").rep(1)).!

  def main(args: Array[String]) {
    println(let.parse("""let foo = myexpr in let foo = myexpr in myexpr"""))
    println(let.parse("blargh"))
  }
}

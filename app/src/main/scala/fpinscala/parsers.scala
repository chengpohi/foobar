package fpinscala

object parsers {

  trait Parser[T]

  trait Parsers[ParseError, Parser[+ _]] {
    self =>
    def run[A](p: Parser[A])(input: String): Either[ParseError, A]
    def char(c: Char): Parser[Char]
    def orString(s1: String, s2: String): Parser[String]
    def or[A](s1: Parser[A], s2: Parser[A]): Parser[A]
    implicit def string(s: String): Parser[String]
    implicit def operators[A](p: Parser[A]) = ParserOps[A](p)
    implicit def asStringParser[A](a: A)(implicit f: A => Parser[String]): ParserOps[String] = ParserOps(f(a))
    def listOfN[A](n: Int, p: Parser[A]): Parser[List[A]]
    def many[A](p: Parser[A]): Parser[List[A]]
    def map[A,B](a: Parser[A])(f: A => B): Parser[B]
    def slice[A](p: Parser[A]): Parser[String]

    case class ParserOps[A](p: Parser[A]) {
      def |[B >: A](p2: Parser[B]): Parser[B] = self.or(p, p2)
      def or[B >: A](p2: => Parser[B]): Parser[B] = self.or(p, p2)
    }

  }

}

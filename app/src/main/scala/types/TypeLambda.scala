package types

object TypeLambda extends App {

  FF.unit(Left(0))
  //throw compiler error type mismatch
  //FF.unit(Left(""))
  FF.unit(Right("right"))

  trait F[M[_]] {
    def unit[A](a: M[A])
  }

  object FF extends F[Either[Int, ?]] {
    override def unit[A](a: Either[Int, A]): Unit = a match {
      case Left(l)  => println(l)
      case Right(c) => println(c)
    }
  }

}

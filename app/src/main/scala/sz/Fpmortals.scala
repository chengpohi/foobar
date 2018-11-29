package sz

import sb.SBook._
import scalaz.Scalaz._
import scalaz._
import scalaz.concurrent.Future


object Fpmortals extends App {
  "Incomprehensible usage" in {
    def getA: Future[Option[Int]] = Future.apply(Some(3))

    def liftFutureOption[A](f: Future[Option[A]]) = OptionT(f)

    def liftFuture[A](f: Future[A]) = f.liftM[OptionT]

    def liftOption[A](o: Option[A]) = OptionT(o.pure[Future])

    def lift[A](a: A) = liftOption(Option(a))

    val res: OptionT[Future, Int] = for {
      a <- getA |> liftFutureOption
      b <- getA |> liftFutureOption
    } yield a * b

    println("foo bar")
  }

  "Data and Functionally" in {
    type |:[L, R] = Either[L, R]

    type Accepted = String |: Long |: Boolean

    final case class Person private(name: String, age: Int)
    object Person {
      def apply(name: String, age: Int): Either[String, Person] = {
        if (name.nonEmpty && age > 0) Right(new Person(name, age))
        else Left(s"bad input: $name, $age")
      } }
    def welcome(person: Person): String =
      s"${person.name} you look wonderful at ${person.age}!"
    val res = for {
      person <- Person("", -1)
    } yield welcome(person)

    println(res)
  }

}

package fpinscala

object Algebra extends App {

  trait Monoid[A] {
    def zero: A
    def op(a: A, b: A): A
  }

  val stringAddition: Monoid[String] = new Monoid[String] {
    override def zero: String = ""
    override def op(a: String, b: String): String = a + b
  }

  val intAddition: Monoid[Int] = new Monoid[Int] {
    override def zero: Int = 0
    override def op(a: Int, b: Int): Int = a + b
  }

  val intMultiplication: Monoid[Int] = new Monoid[Int] {
    override def zero: Int = 1
    override def op(a: Int, b: Int): Int = a * b
  }

  val booleanOr: Monoid[Boolean] = new Monoid[Boolean] {
    override def zero: Boolean = false
    override def op(a: Boolean, b: Boolean): Boolean = a || b
  }

  val booleanAnd: Monoid[Boolean] = new Monoid[Boolean] {
    override def zero: Boolean = true
    override def op(a: Boolean, b: Boolean): Boolean = a || b
  }

  def optionMonoid[A]: Monoid[Option[A]] = new Monoid[Option[A]] {
    override def zero: Option[A] = None
    override def op(a: Option[A], b: Option[A]): Option[A] = a.orElse(b)
  }

  def endoMonoid[A]: Monoid[A => A] = new Monoid[A => A] {
    override def zero: A => A = (i: A) => i
    override def op(a: A => A, b: A => A) = a.andThen(b)
  }

  def foldMap[A, B](as: List[A], m: Monoid[B])(f: A => B): B = {
    as.foldLeft(m.zero)((i, j) => m.op(i, f(j)))
  }

  val res = foldMap((1 to 10).toList, stringAddition)(_.toString)
  assert(res == "12345678910")


  trait Foldable[F[_]] {
    def foldRight[A, B](as: F[A])(z: B)(f: (A, B) => B): B
    def foldLeft[A, B](as: F[A])(z: B)(f: (B, A) => B): B
    def foldMap[A, B](as: F[A])(f: A => B)(mb: Monoid[B]): B
    def concatenate[A](as: F[A])(m: Monoid[A]): A = foldLeft(as)(m.zero)(m.op)
  }

  trait Functor[F[_]] {
    def map[A, B](fa: F[A])(f: A => B): F[B]
    def distribute[A, B](fab: F[(A, B)]): (F[A], F[B]) = {
      (map(fab)(_._1), map(fab)(_._2))
    }
    def codistribute[A, B](fab: Either[F[A], F[B]]): F[Either[A, B]] = {
      fab match {
        case Left(a) => map(a)(Left(_))
        case Right(a) => map(a)(Right(_))
      }
    }
  }

  val listFunctor = new Functor[List] {
    def map[A, B](as: List[A])(f: A => B): List[B] = as map f

  }
}

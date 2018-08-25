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
        case Left(a)  => map(a)(Left(_))
        case Right(a) => map(a)(Right(_))
      }
    }
  }

  val listFunctor = new Functor[List] {
    def map[A, B](as: List[A])(f: A => B): List[B] = as map f

  }

  case class Player(name: String, score: Int)

  sealed trait IO[A] {
    def flatMap[B](f: A => IO[B]): IO[B] =
      FlatMap(this, f)
    def map[B](f: A => B): IO[B] =
      flatMap(f andThen (Return(_)))
  }

  case class Return[A](a: A) extends IO[A]

  case class Suspend[A](resume: () => A) extends IO[A]

  case class FlatMap[A, B](sub: IO[A], k: A => IO[B]) extends IO[B]

  object IO extends Monad[IO] {
    override def unit[A](a: => A): IO[A] = new IO[A] {
      def run = a
    }
    override def flatMap[A, B](fa: IO[A])(f: A => IO[B]) = fa flatMap f
    def apply[A](a: => A): IO[A] = unit(a)
  }

  def fahrenheitToCelsius(f: Double): Double = (f - 32) * 5.0 / 9.0

  def winner(p1: Player, p2: Player): Option[Player] = {
    if (p1.score > p2.score) Some(p1)
    else if (p1.score < p2.score) Some(p2)
    else None
  }

  def winnerMsg(p: Option[Player]): String =
    p map { case Player(name, _) => s"$name is the winner!" } getOrElse "It's a draw."

  def contest(p1: Player, p2: Player): IO[Unit] =
    printLine(winnerMsg(winner(p1, p2)))

  def ReadLine: IO[String] = IO {
    scala.io.StdIn.readLine()
  }

  def printLine(msg: String): IO[Unit] = IO {
    Suspend(() => println(msg))
  }

  def converter: IO[Unit] =
    for {
      _ <- printLine("Enter a temperature in degrees Fahrenheit: ")
      d <- ReadLine.map(_.toDouble)
      _ <- printLine(fahrenheitToCelsius(d).toString)
    } yield ()

  @annotation.tailrec
  def run[A](io: IO[A]): A = io match {
    case Return(a)  => a
    case Suspend(r) => r()
    case FlatMap(x, f) =>
      x match {
        case Return(a)     => run(f(a))
        case Suspend(r)    => run(f(r()))
        case FlatMap(y, g) => run(y flatMap (a => g(a) flatMap f))
      }
    case _ => throw new RuntimeException("")
  }

  val f: Int => IO[Int] = (x: Int) => Return(x)

  val g = List.fill(100000)(f).foldLeft(f) { (a, b) => x: Int =>
    Suspend(() => ()).flatMap { _ =>
      a(x).flatMap(b)
    }
  }
  run(g(42))
}

trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]

  def distribute[A, B](fab: F[(A, B)]): (F[A], F[B]) =
    (map(fab)(_._1), map(fab)(_._2))

  def codistribute[A, B](e: Either[F[A], F[B]]): F[Either[A, B]] = e match {
    case Left(fa)  => map(fa)(Left(_))
    case Right(fb) => map(fb)(Right(_))
  }
}

object Functor {
  val listFunctor = new Functor[List] {
    def map[A, B](as: List[A])(f: A => B): List[B] = as map f
  }
}

trait Monad[F[_]] extends Functor[F] {
  def unit[A](a: => A): F[A]

  def flatMap[A, B](ma: F[A])(f: A => F[B]): F[B] =
    join(map(ma)(f))

  def map[A, B](ma: F[A])(f: A => B): F[B] =
    flatMap(ma)(a => unit(f(a)))
  def map2[A, B, C](ma: F[A], mb: F[B])(f: (A, B) => C): F[C] =
    flatMap(ma)(a => map(mb)(b => f(a, b)))

  def sequence[A](lma: List[F[A]]): F[List[A]] =
    lma.foldRight(unit(List[A]()))((ma, mla) => map2(ma, mla)(_ :: _))

  def traverse[A, B](la: List[A])(f: A => F[B]): F[List[B]] =
    la.foldRight(unit(List[B]()))((a, mlb) => map2(f(a), mlb)(_ :: _))

  // For `List`, the `replicateM` function will generate a list of lists.
  // It will contain all the lists of length `n` with elements selected from the
  // input list.
  // For `Option`, it will generate either `Some` or `None` based on whether the
  // input is `Some` or `None`. The `Some` case will contain a list of length `n`
  // that repeats the element in the input `Option`.
  // The general meaning of `replicateM` is described very well by the
  // implementation `sequence(List.fill(n)(ma))`. It repeats the `ma` monadic value
  // `n` times and gathers the results in a single value, where the monad `M`
  // determines how values are actually combined.

  // Recursive version:
  def _replicateM[A](n: Int, ma: F[A]): F[List[A]] =
    if (n <= 0) unit(List[A]()) else map2(ma, _replicateM(n - 1, ma))(_ :: _)

  // Using `sequence` and the `List.fill` function of the standard library:
  def replicateM[A](n: Int, ma: F[A]): F[List[A]] =
    sequence(List.fill(n)(ma))

  def compose[A, B, C](f: A => F[B], g: B => F[C]): A => F[C] =
    a => flatMap(f(a))(g)

  def _flatMap[A, B](ma: F[A])(f: A => F[B]): F[B] =
    compose((_: Unit) => ma, f)(())

  def join[A](mma: F[F[A]]): F[A] = flatMap(mma)(ma => ma)

  def filterM[A](ms: List[A])(f: A => F[Boolean]): F[List[A]] =
    ms.foldRight(unit(List[A]()))((x, y) =>
      compose(f, (b: Boolean) => if (b) map2(unit(x), y)(_ :: _) else y)(x))
}

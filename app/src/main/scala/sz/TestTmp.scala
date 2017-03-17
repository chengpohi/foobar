package sz

/**
  * scala99
  * Created by chengpohi on 7/2/16.
  */
trait Monoid[A] {
  def mappend(a: A, b: A): A
  def mzero: A
}

object Monoid {
  implicit object IntMonoid extends Monoid[Int] {
    override def mappend(a: Int, b: Int): Int = a + b
    override def mzero: Int = 0
  }

  implicit object StringMonoid extends Monoid[String] {
    override def mappend(a: String, b: String): String = a + b
    override def mzero: String = ""
  }
}

trait FoldLeft[F[_]] {
  def foldLeft[A, B](xs: F[A], b: B, f: (B, A) => B): B
}

object FoldLeft {
  implicit object ListFoldLeft extends FoldLeft[List] {
    override def foldLeft[A, B](xs: List[A], b: B, f: (B, A) => B): B =
      xs.foldLeft(b)(f)
  }
}
trait MonoidOp[A] {
  val F: Monoid[A]
  val value: A
  def |+|(a2: A) = F.mappend(value, a2)
}

object TestTmp {
  implicit def toMonoidOp[A: Monoid](a: A): MonoidOp[A] = new MonoidOp[A] {
    val F = implicitly[Monoid[A]]
    val value = a
  }

  def main(args: Array[String]): Unit = {
    println(3 |+| 4)
    println("Hello" |+| "World")
    println(sum(2, 3))
    println(sum("Hello", "World"))
    println(sum(List("Hello", "World")))
  }

  def sum[A: Monoid](a: A, b: A): A = sum(List(a, b))

  def sum[A: Monoid, M[_]: FoldLeft](xs: M[A]): A = {
    val m = implicitly[Monoid[A]]
    val f1 = implicitly[FoldLeft[M]]
    f1.foldLeft(xs, m.mzero, m.mappend)
  }
}

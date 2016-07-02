package scalaz

/**
  * scala99
  * Created by chengpohi on 7/2/16.
  */

trait Monoid[A] {
  def mappend(a1: A, a2: A): A
  def mzero: A
}

object Monoid {
  implicit object IntMonoid extends Monoid[Int] {
    def mappend(a: Int, b: Int) = a + b
    def mzero: Int = 0
  }

  implicit object StringMonoid extends Monoid[String] {
    def mappend(a: String, b: String): String = a + b
    def mzero: String = ""
  }
}

trait FoldLeft[F[_]] {
  def foldLeft[A, B](xs: F[A], b: B, f: (B, A) => B): B
}

object FoldLeft {
  implicit object FoldLeftList extends FoldLeft[List] {
    def foldLeft[A, B](xs: List[A], b: B, f: (B, A) => B) = xs.foldLeft(b)(f)
  }
}

object TestTmp {
  def main(args: Array[String]): Unit = {
    println(sum(List(1, 2, 3, 4, 5)))
    println(sum(List("a", "b", "c")))
  }

  def sum[T: Monoid, M[_]: FoldLeft](xs: M[T]): T = {
    val m = implicitly[Monoid[T]]
    val f1 = implicitly[FoldLeft[M]]
    f1.foldLeft(xs, m.mzero, m.mappend)
  }
}


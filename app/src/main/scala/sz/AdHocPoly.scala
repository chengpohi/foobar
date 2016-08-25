package sz

/**
  * scala99
  * Created by chengpohi on 8/25/16.
  */
object AdHocPoly {
  def plus[A: Plus](a1: A, a2: A) = implicitly[Plus[A]].plus(a1, a2)
  def main(args: Array[String]): Unit = {
    import AdHoc._
    println(plus("hello", "world"))
    println(plus(1, 2))
    println()
  }
}


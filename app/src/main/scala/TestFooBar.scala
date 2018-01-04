import scala.util.Try


/**
  * scala99
  * Created by chengpohi on 9/20/16.
  */
object TestFooBar extends App {


  def retry[A, B](f: A => B, r: Int = 1): A => B = {
    def repeat(a: A): B = {
      for (_ <- 0 to r) {
        val tried = Try(f(a))
        if (tried.isSuccess) {
          return tried.get
        }
      }
      throw new RuntimeException(s"retry $r failed")
    }
    repeat
  }
  retry((a: String) => a.toInt, 3).apply("2")
}

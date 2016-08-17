package algorithm

/**
  * scala99
  * Created by chengpohi on 8/17/16.
  */
object Knappack {
  def main(args: Array[String]): Unit = {
    val coins = List(1, 2, 5, 10, 20, 50, 100, 200)
    val result = coins.foldLeft(BigInt(1) #:: Stream.fill(200)(BigInt(0))) {
      (s, at) => {
        val (lower, higher) = s splitAt at
        lazy val next: Stream[BigInt] = lower #::: (higher zip next map { case (a, b) => a + b })
        next
      }
    }
    println(result.last)
  }
}

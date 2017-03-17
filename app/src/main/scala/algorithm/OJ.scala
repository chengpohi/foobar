package algorithm

/**
  * scala99
  * Created by chengpohi on 8/17/16.
  */
object OJ {
  def primeStream(s: Stream[Int]): Stream[BigInt] =
    s.head #:: primeStream(s.tail filter {
      _ % s.head != 0
    })
  val primes = primeStream(Stream.from(2))

  def primeFactors(r: Stream[BigInt], n: BigInt): Stream[BigInt] = {
    if (r.head >= n)
      return Stream.empty
    val f: Option[BigInt] = r.find(i => n % i == 0)
    f match {
      case Some(t) => t #:: primeFactors(r.tail, n / t)
      case None => Stream.empty
    }
  }

  val pf = (n: BigInt) => primeFactors(primes, n)

  def main(args: Array[String]): Unit = {
    val n: BigInt = BigInt("600851475143")
    val result: Stream[BigInt] = pf(n)
    println(result.last)
  }
}

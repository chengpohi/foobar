package algorithm

/**
  * scala99
  * Created by chengpohi on 7/24/16.
  */
object DynamicProgramming {
  def main(args: Array[String]): Unit = {
    fib run
  }

  object fib {
    lazy val fibs: Stream[BigInt] = BigInt(0) #:: BigInt(1) #:: fibs.zip(fibs.tail).map { f => f._1 + f._2 }
    def take(n: Int): Stream[BigInt] = fibs take n
    def run(): Unit = {
      println(fib.take(5).toList)
      println(fib.take(6).toList)
      println(fib.take(7).toList)
      println(fib.take(8).toList)
      println(fib.take(9).toList)
    }
  }
}

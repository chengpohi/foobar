package functor

/**
  * scala-parser-combinator
  * Created by chengpohi on 3/20/16.
  */
object ScalazDemo {
  def main(args: Array[String]) {
    import scalaz._
    import effect._
    import IO._
    val action1 = for {
      _ <- putStrLn("Hello, world!")
    } yield ()
    println(action1.unsafePerformIO)

    val action2 = IO {
      val source = scala.io.Source.fromFile("./README.md")
      source.getLines.toStream
    }
    println(action2.unsafePerformIO)
  }
}

package functor

import scalaz.effect._
import scalaz._
import Scalaz._
import IO._

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
      val source = scala.io.Source.fromFile("./users.txt")
      source.getLines.toStream
    }
    println(action2.unsafePerformIO)
    (program |+| program).unsafePerformIO
  }
  def program: IO[Unit] = for {
    line <- readLn
    _ <- putStrLn(line)
  } yield ()
}

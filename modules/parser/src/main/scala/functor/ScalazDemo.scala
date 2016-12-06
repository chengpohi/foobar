package functor

import scalaz.Scalaz._
import scalaz._
import scalaz.effect.IO._
import scalaz.effect.{IO, _}

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
    val m: IO[String] = action2.map(i => i.foldLeft("")(_ + _))
    //(program |+| program).unsafePerformIO
  }
  def program: IO[Unit] = for {
    line <- readLn
    _ <- putStrLn(line)
  } yield ()
}

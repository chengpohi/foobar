package sz.concurrent

import scalaz.Scalaz._
import scalaz.concurrent.Chan._
import scalaz.concurrent._
import scalaz.effect._


object ChanUsage extends App {
  //Asynchronous evaluate function
  def forkIO(f: => IO[Unit])(implicit s: Strategy): IO[Unit] = IO {
    s(f.unsafePerformIO)
    ()
  }

  def calc(chan: Chan[Int], a: Int) =
    chan.write((1 to a).sum)

  val io = for {
    chan <- newChan[Int]
    _ <- calc(chan, 100)
    _ <- calc(chan, 200)
    a <- chan.read
    b <- chan.read
  } yield a + b
  io.unsafePerformIO().println
}

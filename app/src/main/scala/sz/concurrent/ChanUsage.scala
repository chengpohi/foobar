package sz.concurrent

import scalaz.Scalaz._
import scalaz.concurrent.Chan._
import scalaz.concurrent._
import scalaz.effect._

object ChanUsage extends App {
  // val DefaultExecutorService: ExecutorService = {
  //  Executors.newFixedThreadPool(Runtime.getRuntime.availableProcessors, DefaultDaemonThreadFactory)
  //}
  // implicit Executor
  //Asynchronous evaluate function
  Runtime.getRuntime.availableProcessors()
  def forkIO(f: => IO[Unit])(implicit s: Strategy): IO[Unit] = IO {
    s(f.unsafePerformIO)
    ()
  }

  def calc(chan: Chan[Int], a: Int) =
    chan.write((1 to a).sum)

  //chan is thread safe FIFO queue
  val io = for {
    chan <- newChan[Int]
    _ <- forkIO(calc(chan, 100))
    _ <- forkIO(calc(chan, 200))
    a <- chan.read
    b <- chan.read
  } yield a + b
  //IO is used to bound the safe side effect
  //when fina call, we will call unsafePerformIO
  io.unsafePerformIO().println
}

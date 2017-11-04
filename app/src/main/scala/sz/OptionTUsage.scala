package sz

import scala.concurrent.{Await, Future}
import scalaz._
import Scalaz._
import scala.concurrent.duration.Duration


object OptionTUsage extends App {

  import scala.concurrent.ExecutionContext.Implicits.global

  def getA: Future[Option[Int]] = Future {
    Some(1)
  }
  def getB: Future[Option[Int]] = Future {
    Some(1)
  }

  def getC: Future[Int] = Future {
    1
  }

  def getD: Option[Int] = Some(1)

  def liftFutureOption[A](f: Future[Option[A]]) = OptionT(f)
  def liftFuture[A](f: Future[A]) = f.liftM[OptionT]
  def liftOption[A](o: Option[A]) = OptionT(o.pure[Future])
  def lift[A](a: A)               = liftOption(Option(a))

  val res: OptionT[Future, Int] = for {
    a <- getA |> liftFutureOption
    b <- getA |> liftFutureOption
    c <- getC |> liftFuture
    d <- getD |> liftOption
    e <- 1 |> lift
  } yield a + b + c + d

  val r = Await.result(res.run, Duration.Inf)
  println(r)
}

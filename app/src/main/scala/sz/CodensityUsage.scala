package sz

import scalaz.Codensity
import scalaz.effect.{IO, SafeApp}
import scalaz._
import Scalaz._

object CodensityUsage extends SafeApp {
  def bracketCodensity[A, B](before: IO[A])(after: A => IO[B]): Codensity[IO, A] =
    new Codensity[IO, A] {
      def apply[C](f: A => IO[C]): IO[C] =
        before.bracket(after)(f) // store the instructions
    }

  def resource(s: String): Codensity[IO, String] =
    bracketCodensity(
      IO.putStrLn(s"Acquire resource: $s") >| s
    )(
      s0 => IO.putStrLn(s"Release resource: $s0")
    )

  val t = IO {
    "Hello"
  }.map(_.toUpperCase())
    .bracket(s => IO.putStrLn(s))(s => IO.putStrLn(""))
    .unsafePerformIO()

  //get the Condesity type resource
  val resource1 = resource("R8")
  resource1.map(s => {
    println(s.toUpperCase()) // during do something
  }).improve.unsafePerformIO() // finally release it

  val prg = for {
    r1 <- resource("R1")
    r2 <- resource("R2")
    rs <- (3 to 6).toList.traverseU(s => resource(s"R$s"))
    _ <- IO.putStrLn("Acquired resources:").liftM[Codensity]
    _ <- (r1 :: r2 :: rs).traverse(IO.putStrLn).liftM[Codensity]
  } yield ()

  prg.improve.unsafePerformIO()

  println("Hello World")
}

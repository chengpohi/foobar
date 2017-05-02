package sz

object ValidationUsage extends App {

  import scalaz._

  import Validation.FlatMap._


  def parseInt(s: String): Validation[String, Int] = try {
    Success(s.toInt)
  } catch {
    case ex: NumberFormatException => Failure(ex.getMessage)
  }

  val V = Applicative[ValidationNel[String, ?]]

  val x: ValidationNel[String, Int] = V.apply2(
    parseInt("1.x").toValidationNel, // wrap the failure to the list
    parseInt("1..0").toValidationNel)(_ * _)

  println(x)


  def isEven(n: Int): Validation[String, Int] = if (n % 2 == 0) Success(n) else Failure("it's not even")

  def isGT10(n: Int): Validation[String, Int] = if (n > 10) Success(n) else Failure("it's less than or equal to 10")

  val a: String => Validation[String, Int] = (a: String) => for {
    x <- parseInt(a)
    y <- isEven(x)
    z <- isGT10(y)
  } yield z

  println(a("1"))
  println(a("10"))

  import scalaz._
  import Scalaz._

  val b: String => ValidationNel[String, Int] = (a: String) => parseInt(a).toValidationNel.flatMap(i => isEven(i).toValidationNel +++ isGT10(i).toValidationNel)
  println(b("7"))
  println(b("11"))
  println(b("20"))
}

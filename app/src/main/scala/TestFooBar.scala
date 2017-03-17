import scala.reflect.runtime.universe._

/**
  * scala99
  * Created by chengpohi on 9/20/16.
  */
object TestFooBar {
  def main(args: Array[String]): Unit = {
    implicit val tag = typeOf[Int]
    val cast: Option[Int] = tryCast(1)
    val cast2: Option[String] = tryCast("hello")
    println(cast2)
  }

  def tryCast[A](o: A)(implicit tpe: Type, ta: TypeTag[A]): Option[A] =
    ta.tpe =:= tpe match {
      case true => Some(o)
      case false => None
    }
}

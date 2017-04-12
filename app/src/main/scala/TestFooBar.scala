import scala.reflect.runtime.universe._

/**
  * scala99
  * Created by chengpohi on 9/20/16.
  */
object TestFooBar {
  case class A(attr1: String)
  def foo[T](t: T)(implicit typeTag: TypeTag[T]): Any = {
    typeTag.tpe.members
  }

  def main(args: Array[String]): Unit = {
    val a = A("1")
  }
}

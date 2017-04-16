import scala.reflect.runtime.universe._

/**
  * scala99
  * Created by chengpohi on 9/20/16.
  */

class A(a: TestFunctionalInterface[String])

class B(c: String => String) extends A((t: String) => c.apply(t))

object TestFooBar {

  case class A(attr1: String)

  def foo[T](t: T)(implicit typeTag: TypeTag[T]): Any = {
    typeTag.tpe.members
  }

  def main(args: Array[String]): Unit = {
    val s: String => String = s => s.toUpperCase
    val interface = new TestFunctionalInterface[String] {
      override def apply(t: String): String = s.apply(t)
    }
    new B(s)
    val a = A("1")
  }
}

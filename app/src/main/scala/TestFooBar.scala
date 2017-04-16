import scala.reflect.runtime.universe._

/**
  * scala99
  * Created by chengpohi on 9/20/16.
  */

class B(c: String => String) extends TestTmp((s: String) => c.apply(s))

object TestFooBar {


  def foo[T](t: T)(implicit typeTag: TypeTag[T]): Any = {
    typeTag.tpe.members
  }

  def main(args: Array[String]): Unit = {
    val s: String => String = s => s.toUpperCase
    new B(s)
  }
}

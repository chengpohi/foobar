import org.scalatest.words.ResultOfStringPassedToVerb
import org.scalatest.{FlatSpec, Matchers}

class FooBarTest extends FlatSpec with Matchers {
  val m1: ResultOfStringPassedToVerb = "a" should "b"

  val m2 = "a" should "b"

  //    println(m1)
  //    println(m2)

  import scala.reflect.runtime.universe._
  val a1 = new A("name")
  val a2 = new A("name")
  it should "" in {
    println(a1 == a2)
    println(typeOf[ResultOfStringPassedToVerb])
    if (m1.equals(m2)) {
      println("both are equal")
    } else {
      println("both are different")
    }
  }

}

class A(val name: String)

import org.scalatest.{FunSuite, Matchers}

class WithoutEqualizerConversion extends FunSuite with Matchers {
}

class FooBarTest extends FunSuite {

  override def convertToEqualizer[T](left: T): Equalizer[T] = new Equalizer(left)

  import scalaz._
  import Scalaz._

  test("=== operator of Scalaz") {
    assert(1 === 1) // i want to check/test === operator of Scalaz
  }

}

class A(val name: String)

import org.scalatest.{ShouldMatchers, FlatSpec}

/**
 * scala99
 * Created by chengpohi on 10/7/15.
 */
class Scala99Test extends FlatSpec with ShouldMatchers {
  val scala99 = new Scala99
  val numbers: List[Int] = List(1, 2, 3, 4, 5)
  val palindromeNumbers = List(1, 2, 3, 2, 1)

  it should " list" in {
    scala99 lastOne numbers should equal(5)
    scala99 kTh(numbers, 3) should equal(3)
    numbers.size should equal(5)
    scala99 reverse numbers should equal(List(5, 4, 3, 2, 1))
    scala99 palindrome palindromeNumbers should equal(true)
    scala99 palindrome numbers should equal(false)
    scala99 palindrome List() should equal(true)
    scala99 flatten List(1, 2, List(3, 4)) should equal(List(1, 2, 3, 4))
    scala99 compress List(1, 2, 2, 3, 3) should equal(List(1, 2, 3))
    scala99 pack List(1, 2, 2, 3, 3) should equal(List(List(2, 2), List(1), List(3, 3)))
    List(Array(2, 2), Array(1, 1), Array(2, 3)) should contain(Array(2, 2))
    scala99 encode List(1, 2, 2, 3, 3) should equal(List((2, 2), (1, 1), (2, 3)))
    scala99 encode List("1", "2", "2", "3", "3") should equal(List((2, "2"), (1, "1"), (2, "3")))
    scala99 encodeModified List(1, 2, 2, 3, 3) should equal(List((2, 2), 1, (2, 3)))
    scala99 decodeModified List((2, 2), (1, 1), (2, 3)) should equal(List(2, 2, 1, 3, 3))
    scala99 duplicate List(1, 2, 2, 3, 3) should equal(List(2, 2, 2, 2, 1, 1, 3, 3, 3, 3))
    scala99 duplicate(List(1, 2, 2, 3, 3), 3) should equal(List(2, 2, 2, 2, 2, 2, 1, 1, 1, 3, 3, 3, 3, 3, 3))
    scala99 remove(List(1, 2, 2, 3, 3, 4), 3) should equal(List(1, 2, 3, 3))
    scala99 split(List(1, 2, 2, 5, 3, 4), 3) should equal((List(1, 2, 2), List(5, 3, 4)))
    scala99 rotate(List(1, 2, 3, 4, 5), 3) should equal(List(4, 5, 1, 2, 3))
    scala99 removeByIndex(List(1, 2, 3, 4, 5), 3) should equal(List(1, 2, 4, 5))
    scala99 insert(List(1, 2, 3, 4, 5), 8, 3) should equal(List(1, 2, 3, 8, 4, 5))
    numbers should contain((scala99 randSelect(numbers, 1)).head)

    (scala99 randSelectDiff(5, 20)).size should equal(5)
    (scala99 randPermu List(1, 2, 3, 4, 5)).distinct.size should equal(5)
  }
}

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
    scala99 combination(List(1, 2, 3, 4, 5), 3) should equal(List(List(1, 2, 3), List(1, 2, 4), List(1, 2, 5), List(1, 3, 4), List(1, 3, 5), List(1, 4, 5), List(2, 3, 4), List(2, 3, 5), List(2, 4, 5), List(3, 4, 5)))
    scala99 group(List(1, 2, 3, 4, 5), List(2, 2, 1)) should equal(List(List(List(1, 2), List(3, 4), List(5)), List(List(1, 2), List(3, 5), List(4)), List(List(1, 2), List(4, 5), List(3)), List(List(1, 3), List(2, 4), List(5)), List(List(1, 3), List(2, 5), List(4)), List(List(1, 3), List(4, 5), List(2)), List(List(1, 4), List(2, 3), List(5)), List(List(1, 4), List(2, 5), List(3)), List(List(1, 4), List(3, 5), List(2)), List(List(1, 5), List(2, 3), List(4)), List(List(1, 5), List(2, 4), List(3)), List(List(1, 5), List(3, 4), List(2)), List(List(2, 3), List(1, 4), List(5)), List(List(2, 3), List(1, 5), List(4)), List(List(2, 3), List(4, 5), List(1)), List(List(2, 4), List(1, 3), List(5)), List(List(2, 4), List(1, 5), List(3)), List(List(2, 4), List(3, 5), List(1)), List(List(2, 5), List(1, 3), List(4)), List(List(2, 5), List(1, 4), List(3)), List(List(2, 5), List(3, 4), List(1)), List(List(3, 4), List(1, 2), List(5)), List(List(3, 4), List(1, 5), List(2)), List(List(3, 4), List(2, 5), List(1)), List(List(3, 5), List(1, 2), List(4)), List(List(3, 5), List(1, 4), List(2)), List(List(3, 5), List(2, 4), List(1)), List(List(4, 5), List(1, 2), List(3)), List(List(4, 5), List(1, 3), List(2)), List(List(4, 5), List(2, 3), List(1))))
    scala99 lsort List(List(1, 2), List(3), List(4, 5, 6)) should equal(List(List(3), List(1, 2), List(4, 5, 6)))
    scala99 lfsort List(List(1, 2), List(7, 8), List(3), List(9), List(4, 5, 6)) should equal(List(List(4, 5, 6), List(1, 2), List(7, 8), List(3), List(9)))
    scala99 isPrime 7 should equal(true)
    scala99 isPrime 4 should equal(false)
    scala99 primeFactors 15 should equal(List(3, 5))
    scala99 primeFactors 315 should equal(List(3, 3, 5, 7))
    scala99 primeFactors 5 should equal(List(5))
    scala99 primeFactors 2 should equal(List(2))
    scala99 primeFactorsMulti 315 should equal(List((3, 2), (5, 1), (7, 1)))
    scala99 primeRange (2, 11) should equal(List(2, 3, 5, 7, 11))
  }
}

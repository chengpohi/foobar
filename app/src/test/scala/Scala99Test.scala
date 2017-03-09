import algorithm.{Node, Scala99}
import org.scalatest.{FlatSpec, Matchers}

/**
  * scala99
  * Created by chengpohi on 10/7/15.
  */
class Scala99Test extends FlatSpec with Matchers {
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
    scala99 primeRange(2, 11) should equal(List(2, 3, 5, 7, 11))
    scala99 goldBanch 28 should equal((5, 23))
    scala99 goldBanchEvenList(9, 20) should equal(List((3, 7), (5, 7), (3, 11), (3, 13), (5, 13), (3, 17)))
    scala99 gcd ((36, 63)) should equal(9)
    scala99 coprime ((35, 64)) should equal(true)
    scala99 totiendPhi 10 should equal(4)
    scala99 gray 1 should equal(List("0", "1"))
    scala99 gray 2 should equal(List("00", "01", "10", "11"))
    scala99 gray 3 should equal(List("000", "001", "010", "011", "100", "101", "110", "111"))
    val l = List(("a", 45), ("b", 13), ("c", 12), ("d", 16), ("e", 9), ("f", 5))
    scala99 huffman l should equal(List(("f", "0000"), ("e", "0001"), ("c", "001"), ("b", "010"), ("d", "011"), ("a", "1")))

    val left: Node[String] = Node(None, None, 1, Some(""))
    val right: Node[String] = Node(None, None, 1, Some(""))
    val trees = Node(Some(left), Some(right), 1, Some(""))

    scala99 countLeaves trees should equal(2)
    scala99 countLeaves left should equal(1)
    scala99 countLeaves Node(Some(Node(Some(left), Some(right), 1, Some(""))), Some(Node(Some(left), Some(right), 1, Some(""))), 1, Some("")) should equal(4)
    (scala99 completeBinaryTrees (6)).size should equal(4)
    (scala99 completeBinaryTrees (5)).size should equal(4)
    (scala99 completeBinaryTrees (4)).size should equal(4)
    (scala99 completeBinaryTrees (3)).size should equal(1)
    (scala99 completeBinaryTrees (2)).size should equal(2)
    (scala99 completeBinaryTrees (1)).size should equal(1)
  }

  it should "stream" in {
    scala99.fibsNoMemoization.foreach(println)
  }
}

trait Foo {

}

trait Bar extends Foo {
  this: Foo =>
  {

  }
}

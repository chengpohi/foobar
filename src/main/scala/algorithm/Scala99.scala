package algorithm

import scala.collection.mutable
import scala.util.Random

/**
  * Default (Template) Project
  * Created by chengpohi on 7/2/16.
  */
class Scala99 {
  def lastOne[T](list: List[T]): T = {
    list.last
  }

  def kTh[T](list: List[T], k: Int): T = {
    list(k - 1)
  }

  def reverse[T](list: List[T]): List[T] = list match {
    case al :+ a0 => a0 :: reverse(al)
    case Nil => Nil
  }

  def palindrome[T](list: List[T]): Boolean = list match {
    case List(a) => true
    case head :: ar => head == ar.last && palindrome(ar.init)
    case List() => true
    case _ => false
  }

  def flatten[T](list: List[T]): List[Any] = list flatMap {
    case a: List[_] => flatten(a)
    case b => List(b)
  }

  def compress[T](list: List[T]): List[T] = list.distinct

  def pack[T](list: List[T]): List[List[T]] = {
    list.groupBy(a => a).values.toList
  }

  def encode[T](list: List[T]): List[(Int, T)] = {
    list.groupBy(a => a).toList.map(f => (f._2.size, f._1))
  }

  def encodeModified[T](list: List[T]): List[Any] = {
    list.groupBy(a => a).toList.map {
      case f if f._2.size == 1 => f._1
      case f => (f._2.size, f._1)
    }
  }

  def duplicate[T](list: List[T]): List[T] = {
    list.groupBy(a => a).toList.flatMap(f => (for (i <- 1 to f._2.size * 2) yield f._1).toList)
  }

  def duplicate[T](list: List[T], n: Int): List[T] = {
    list.groupBy(a => a).toList.flatMap(f => (for (i <- 1 to f._2.size * n) yield f._1).toList)
  }

  def remove[T](list: List[T], i: Int): List[T] = {
    list.zipWithIndex.filter(n => (n._2 + 1) % i != 0).map(f => f._1)
  }

  def split[T](list: List[T], i: Int): (List[T], List[T]) = {
    list.splitAt(i)
  }

  def rotate[T](list: List[T], i: Int): List[T] = {
    val l: (List[T], List[T]) = list.splitAt(i)
    l._2 ::: l._1
  }

  def removeByIndex[T](list: List[T], i: Int): List[T] = {
    list.patch(i - 1, Nil, 1)
  }

  def insert[T](list: List[T], a: T, i: Int): List[T] = {
    val l: (List[T], List[T]) = list.splitAt(i)
    (l._1 :+ a) ::: l._2
  }

  def randSelect[T](list: List[T], a: Int): List[T] = {
    val s = new mutable.LinkedHashSet[Int]()
    val max = list.size
    while (s.size < a) {
      val nextInt: Int = Random.nextInt(max)
      if (!s.contains(nextInt)) {
        s.add(nextInt)
      }
    }
    s.toList.map(i => list(i))
  }

  def randSelectDiff(a: Int, max: Int): List[Int] = {
    val s = new mutable.LinkedHashSet[Int]()
    while (s.size < a) {
      val nextInt: Int = Random.nextInt(max)
      if (!s.contains(nextInt)) {
        s.add(nextInt)
      }
    }
    s.toList
  }

  def randPermu[T](list: List[T]): List[T] = {
    randSelect(List.range(0, list.size), list.size).map(i => list(i))
  }

  def combination[T](list: List[T], n: Int): List[List[T]] = {
    list.combinations(n).toList
  }

  def group[T](list: List[T], groupCount: List[Int]): List[List[List[Any]]] = {
    groupCount match {
      case List(head) =>
        List(List(list))
      case head :: l =>
        val groups = list.combinations(head).toList
        groups.flatMap(g => group(list diff g, l).map(i => g :: i))
    }
  }

  def lsort[T](list: List[List[T]]): List[List[T]] = {
    list.sortWith(_.size < _.size)
  }

  def lfsort[T](list: List[List[T]]): List[List[T]] = {
    list.groupBy(a => a.size).toList.sortBy(i => i._2.size).flatMap(i => i._2)
  }

  def decodeModified[T](list: List[(Int, T)]): List[T] = {
    list.flatMap(f => (for (i <- 1 to f._1) yield f._2).toList)
  }

  def isPrime(n: Int): Boolean = {
    (2 to n / 2).forall(i => n % i != 0)
  }

  def primeFactors(n: Int): List[Int] = {
    n / 2 match {
      case i if i >= 1 =>
        val r = (2 to n + 1).view.filter(i => n % i == 0).head
        r :: primeFactors(n / r)
      case _ => List()
    }
  }

  def primeFactorsMulti(n: Int): List[(Int, Int)] = {
    primeFactors(n).groupBy(i => i).map(i => (i._1, i._2.size)).toList.sortBy(i => i._1)
  }

  def primeRange(start: Int, end: Int): List[Int] = {
    (start to end + 1).filter(i => isPrime(i)).toList
  }

  def goldBanch(number: Int): (Int, Int) = {
    val x = primeRange(2, number).filter(i => isPrime(number - i)).head
    (x, number - x)
  }

  def goldBanchEvenList(lower: Int, upper: Int): List[(Int, Int)] = {
    (lower to upper).filter(i => i % 2 == 0).map(i => goldBanch(i)).toList
  }

  def gcd(n: (Int, Int)): Int = {
    val max = Math.min(n._1, n._2) + 1
    (1 to max).filter(i => n._1 % i == 0 && n._2 % i == 0).last
  }

  def coprime(n: (Int, Int)): Boolean = {
    gcd(n) == 1
  }

  def totiendPhi(n: Int): Int = {
    (1 to n).filter(i => coprime((i, n))).toList.size
  }

  def gray(n: Int): List[String] = {
    val c = List(0, 1)
    def bits(n: Int): List[List[Int]] = n match {
      case x if x > 1 => c.flatMap(i =>
        bits(n - 1).map(b =>
          i :: b
        )
      )
      case 1 => c.map(i => List(i))
    }
    bits(n).map(i => i.mkString(""))
  }

  def huffman[T](fre: List[(T, Int)]): List[(T, String)] = {
    def huffmanNode[T](fre: List[Node[T]]): Node[T] = {
      fre.sortBy(f => f.value) match {
        case left :: (right :: l) =>
          val newFre: List[Node[T]] = Node(Some(left), Some(right), left.value + left.value, None) :: l
          huffmanNode(newFre)
        case List(left, right) => Node[T](Some(left), Some(right), left.value + right.value, None)
        case List(result) => result
      }
    }

    def traverse[T](tree: Node[T]): List[(T, String)] = {
      tree match {
        case Node(Some(left), Some(right), value, key) =>
          val l = traverse(left).map(t => (t._1, "0" + t._2))
          val r = traverse(right).map(t => (t._1, "1" + t._2))
          l ::: r
        case Node(Some(left), None, value, key) => traverse(left).map(t => (t._1, "0" + t._2))
        case Node(None, Some(right), value, key) => traverse(right).map(t => (t._1, "1" + t._2))
        case Node(None, None, value, Some(key)) => List((key, ""))
      }
    }
    val node: List[Node[T]] = fre.sortBy(t => t._2).map(f => Node(None, None, f._2, Some(f._1)))
    val n = huffmanNode(node)
    traverse(n)
  }

  def countLeaves[T](binaryTree: Node[T]): Int = binaryTree match {
    case Node(Some(left), Some(right), value, key) =>
      countLeaves(left) + countLeaves(right)
    case Node(Some(left), None, value, key) => countLeaves(left)
    case Node(None, Some(right), value, key) => countLeaves(right)
    case Node(None, None, value, Some(key)) => 1
  }

  def completeBinaryTrees(number: Int): List[Node[String]] = {
    def generateNode(n: Int): List[Node[String]] = {
      n match {
        case 1 => List(Node(None, None, 1, Some("leaf")))
        case 2 =>
          List(Node(None, Some(generateNode(1).head), 1, Some("node")), Node(Some(generateNode(1).head), None, 1, Some("node")))
        case n1 if n1 % 2 == 0 =>
          val n2: Int = (n1 - 1) % 2 + 1
          val n3: Int = (n1 - 1) % 2

          val left1 = generateNode(n2)
          val right1 = generateNode(n3)
          val left2 = generateNode(n3)
          val right2 = generateNode(n2)
          val l1 = left1.flatMap(l => right1.map(r => Node(Some(l), Some(r), 1, Some("node"))))
          val l2 = left2.flatMap(l => right2.map(r => Node(Some(l), Some(r), 1, Some("node"))))
          l1 ::: l2
        case n1 if n1 % 2 == 1 =>
          val nodes: Int = (n1 - 1) / 2
          val left = generateNode(nodes)
          val right = generateNode(nodes)
          left.flatMap(l => right.map(r => Node(Some(l), Some(r), 1, Some("node"))))
      }
    }
    generateNode(number)
  }
}

import scala.collection.mutable
import scala.util.Random

/**
 * scala99
 * Created by chengpohi on 10/7/15.
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

  def decodeModified[T](list: List[(Int, T)]): List[T] = {
    list.flatMap(f => (for (i <- 1 to f._1) yield f._2).toList)
  }
}

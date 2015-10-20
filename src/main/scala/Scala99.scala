/**
 * scala99
 * Created by chengpohi on 10/7/15.
 */
class Scala99 {
  def lastOne[T](arrs: List[T]): T = {
    arrs.last
  }

  def kTh[T](arrs: List[T], k: Int): T = {
    arrs(k - 1)
  }

  def reverse[T](arrs: List[T]): List[T] = arrs match {
    case al :+ a0 => a0 :: reverse(al)
    case Nil => Nil
  }

  def palindrome[T](arrs: List[T]): Boolean = arrs match {
    case List(a) => true
    case head :: ar => head == ar.last && palindrome(ar.init)
    case List() => true
    case _ => false
  }

  def flatten[T](arrs: List[T]): List[Any] = arrs flatMap {
    case a: List[_] => flatten(a)
    case b => List(b)
  }

  def compress[T](arrs: List[T]): List[T] = arrs.distinct

  def pack[T](arrs: List[T]): List[List[T]] = {
    arrs.groupBy(a => a).values.toList
  }

  def encode[T](arrs: List[T]): List[(Int, T)] = {
    arrs.groupBy(a => a).toList.map(f => (f._2.size, f._1))
  }

  def encodeModified[T](arrs: List[T]): List[Any] = {
    arrs.groupBy(a => a).toList.map {
      case f if f._2.size == 1 => f._1
      case f => (f._2.size, f._1)
    }
  }

  def decodeModified[T](arrs: List[(Int, T)]): List[T] = {
    arrs.flatMap(f => (for (i <- 1 to f._1) yield f._2).toList)
  }
}

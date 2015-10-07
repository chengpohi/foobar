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

  def flatten[T](arrs: List[T]): List[T] = arrs flatMap {
    case a: List[T] => flatten(a)
    case b => List(b)
  }

  def compress[T](arrs: List[T]): List[T] = arrs.distinct
}

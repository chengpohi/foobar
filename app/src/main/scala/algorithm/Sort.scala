package algorithm

import scalaz._
import Scalaz._
import scalaz.{Equal, Order}

/**
  * scala99
  * Created by chengpohi on 7/30/16.
  */
object Sort {
  def main(args: Array[String]): Unit = {
    val sort: SelectionSort = new SelectionSort
    val result = sort.ssort(Stream(1, 5, 4, 3, 2)).toList
    println(result)
  }
}

class SelectionSort {
  def minimumS[A: Order](stream: Stream[A]) = stream match {
    case x #:: xs => xs.foldLeft(x) { _ min _ }
  }
  def deleteS[A: Equal](y: A, stream: Stream[A]): Stream[A] =
    (y, stream) match {
      case (_, Stream()) => Stream()
      case (y, x #:: xs) =>
        if (y === x) xs
        else x #:: deleteS(y, xs)
    }
  def delmin[A: Order](stream: Stream[A]): Option[(A, Stream[A])] =
    stream match {
      case Stream() => none
      case xs =>
        val y = minimumS(xs)
        (y, deleteS(y, xs)).some
    }
  def ssort[A: Order](stream: Stream[A]): Stream[A] = unfold(stream) {
    delmin[A]
  }
}

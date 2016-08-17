package sz

import scalaz._
import Scalaz._
import Monad._
import State._
import Applicative._

/**
  * scala99
  * Created by chengpohi on 8/13/16.
  */
object Applic {
  def main(args: Array[String]): Unit = {
    val f = (i: Int) => List(i)
    val tree = Bin(Leaf(1), Leaf(2))
  }
}

sealed trait BinaryTree[A]
case class Leaf[A](a: A) extends BinaryTree[A]
case class Bin[A](left: BinaryTree[A], right: BinaryTree[A]) extends BinaryTree[A]

package sz

import scalaz.Applicative

/**
  * scala99
  * Created by chengpohi on 8/13/16.
  */
class Applic {

}

sealed trait BinaryTree[A]
case class Leaf[A](a: A) extends BinaryTree[A]
case class Bin[A](left: BinaryTree[A], right: BinaryTree[A]) extends BinaryTree[A]

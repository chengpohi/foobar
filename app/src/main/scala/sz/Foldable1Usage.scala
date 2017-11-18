package sz

import scalaz.std.anyVal._
import scalaz.std.option._
import scalaz.std.string._
import scalaz.syntax.equal._
import scalaz.syntax.foldable1._
import scalaz.{Foldable1, IList, NonEmptyList, OneAnd}

object Foldable1Usage extends App {
  assert(
    Foldable1[NonEmptyList].foldMap1(NonEmptyList(1, 2, 3))(identity) === 6)
  assert(
    Foldable1[NonEmptyList]
      .foldMap1(NonEmptyList("1", "2", "3"))(identity) === "123")
  assert(NonEmptyList(1, 2, 3).foldMap1(identity) === 6)
  assert(NonEmptyList("1", "2", "3").foldMap1(identity) === "123")

  assert(NonEmptyList(1, 2, 3).foldMap1(identity) === 6)
  assert(NonEmptyList("1", "2", "3").foldMap1(identity) === "123")

  // or with a slightly less trivial function:
  assert(
    Foldable1[NonEmptyList]
      .foldMap1(NonEmptyList(1, 2, 3))(_.toString) === "123")
  assert(NonEmptyList(1, 2, 3).foldMap1(_.toString) === "123")

  sealed trait Tree[A]

  sealed case class N[A](left: Tree[A], right: Tree[A]) extends Tree[A]

  sealed case class L[A](a: A) extends Tree[A]

  assert(NonEmptyList(1, 2, 3, 4).foldMapRight1[Tree[Int]](L.apply)((a, b) ⇒
    N(L(a), b)) == N(L(1), N(L(2), N(L(3), L(4)))))
  assert(NonEmptyList(1, 2, 3, 4).minimum === Some(1))
  assert(Foldable1[NonEmptyList].minimum1(NonEmptyList(1, 2, 3, 4)) === 1)
  assert(NonEmptyList(1, 2, 3, 4).minimum1 === 1)

  assert(NonEmptyList(1, 2, 3, 4).maximum === Some(4))
  assert(Foldable1[NonEmptyList].maximum1(NonEmptyList(1, 2, 3, 4)) === 4)
  assert(NonEmptyList(1, 2, 3, 4).maximum1 === 4)

  assert(NonEmptyList("a", "aa", "aaa").minimumBy(_.length) === Some("a"))
  assert(
    Foldable1[NonEmptyList]
      .minimumBy1(NonEmptyList("a", "aa", "aaa"))(_.length) === "a")
  assert(NonEmptyList("a", "aa", "aaa").minimumBy1(_.length) === "a")

  assert(NonEmptyList("a", "aa", "aaa").maximumBy(_.length) === Some("aaa"))
  assert(
    Foldable1[NonEmptyList]
      .maximumBy1(NonEmptyList("a", "aa", "aaa"))(_.length) === "aaa")
  assert(NonEmptyList("a", "aa", "aaa").maximumBy1(_.length) === "aaa")

  assert(OneAnd(1, IList(2, 3)).foldMap1(identity) === 6)
}

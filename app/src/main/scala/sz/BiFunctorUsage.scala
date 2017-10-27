package sz

import scalaz._
import Scalaz._

//F[A, B] fa: A => C, fb: B => D = F[C, D]
object BiFunctorUsage extends App {

  // For a tuple, the result of bimap is obvious:
  assert(
    Bifunctor[Tuple2].bimap(("asdf", 1))(_.toUpperCase, _ + 1) === ("ASDF", 2))
  // For sum types, which function is applied depends on what value is present:
  assert(
    Bifunctor[Either].bimap(Left("asdf"): Either[String, Int])(
      _.toUpperCase,
      _ + 1) === (Left("ASDF")))
  assert(Bifunctor[Either]
    .bimap(Right(1): Either[String, Int])(_.toUpperCase, _ + 1) === (Right(2)))
  Bifunctor[Either]
    .bimap(Right(1): Either[String, Int])(_.toUpperCase, _ + 1)
    .println

  assert(
    Bifunctor[Validation]
      .bimap("asdf".failure[Int])(_.toUpperCase, _ + 1) === "ASDF".failure)
  assert(
    Bifunctor[Validation]
      .bimap(1.success[String])(_.toUpperCase, _ + 1) === 2.success)

  assert(
    Bifunctor[\/].bimap("asdf".left[Int])(_.toUpperCase, _ + 1) === "ASDF".left)
  assert(Bifunctor[\/].bimap(1.right[String])(_.toUpperCase, _ + 1) === 2.right)

  // There is syntax for bimap:
  assert(("asdf", 1).bimap(_.length, _ + 1) === (4, 2))

  // Bifunctors are covariant in both their type parameters,
  // which is expressed by widen
  // covariant to (Any, Any) type
  assert(("asdf", 1).widen[Any, Any].isInstanceOf[(Any, Any)])

  // There are functions to only map the "right" or "left" value only:
  assert(Bifunctor[Tuple2].leftMap(("asdf", 1))(_.substring(1)) === ("sdf", 1))
  assert(Bifunctor[Tuple2].rightMap(("asdf", 1))(_ + 3) === ("asdf", 4))

  // These come with syntax.
  assert(1.success[String].rightMap(_ + 10) === 11.success)
  assert(("a", 1).rightMap(_ + 10) === ("a", 11))

  // and some even fancier syntax
  val two = 1.success[String] :-> (_ + 1)
  assert(two === 2.success)

  // On the left side, the type inference can be bad, so that we are
  // forced to be explicit about the types on the function we leftMap.
  val strlen: String => Int = _.length

  //left
  assert((strlen <-: ("asdf", 1)) === (4, 1))
  //right
  assert((((_: String).length) <-: ("asdf", 1)) === (4, 1))

  //left and right
  val fourTwo = strlen <-: ("asdf", 1) :-> (_ + 1)
  assert(fourTwo === (4, 2))

  // We can compose a functor with a bifunctor to get a new bifunctor.
  // For example, if we have a list of a type for which we have a
  // bifunctor, we can get a bimap that operates on every item in the
  // list.
  val bff = Functor[List] bicompose Bifunctor[\/]

  val bfres = bff.bimap(List("asdf".left, 2.right, "qwer".left, 4.right))(
    _.toUpperCase,
    _ + 1)
  assert(bfres === List("ASDF".left, 3.right, "QWER".left, 5.right))

  // We can get at the either the left or right underlying functors.
  val leftF = Bifunctor[\/].leftFunctor[String]
  assert(leftF.map("asdf".right[Int])(_ + 1) === "asdf".right)
  assert(leftF.map(1.left)(_ + 1) === 2.left)

  val rightF = Bifunctor[\/].rightFunctor[String]
  assert(rightF.map("asdf".left[Int])(_ + 1) === "asdf".left)
  assert(rightF.map(1.right)(_ + 1) === 2.right)

  //
  // Ufunctor
  //

  // If we have an F[A,A] (instead of F[A,B] with A and B different)
  // we can extract a "unified functor" which is a functor,
  assert(Bifunctor[Tuple2].uFunctor.map((2, 3))(_ * 3) === (6, 9))

  // or skip the step of extracting the unified functor using the umap method.
  assert(Bifunctor[Tuple2].umap((2, 3))(_ * 3) === (6, 9))

}

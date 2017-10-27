package sz

object TraverseUsage extends App {

  import scalaz._

  import scalaz.std.list._
  import scalaz.std.vector._
  import scalaz.std.option._
  import scalaz.std.anyVal._
  import scalaz.std.string._
  // for === syntax
  import scalaz.syntax.equal._
  // for .success and .failure syntax
  import scalaz.syntax.validation._

  val list1: List[Option[Int]] = List(Some(1), Some(2), Some(3), Some(4))
  assert(Traverse[List].sequence(list1) === Some(List(1, 2, 3, 4)))

  val list2: List[Option[Int]] = List(Some(1), Some(2), None, Some(4))
  assert(Traverse[List].sequence(list2) === None)

  import scalaz.syntax.traverse._

  assert(list1.sequence === Some(List(1, 2, 3, 4)))
  assert(list1.sequence.sequence === list1)

  val smallNumbers = List(1, 2, 3, 4, 5)
  val bigNumbers = List(10, 20, 30, 40, 50)
  val doubleSmall: Int => Option[Int] = (x => if (x < 30) Some(x * 2) else None)

  assert(smallNumbers.traverse(doubleSmall) === Some(List(2, 4, 6, 8, 10)))
  assert(
    smallNumbers
      .traverse(doubleSmall) === smallNumbers.map(doubleSmall).sequence)

  val validations: Vector[ValidationNel[String, Int]] =
    Vector(1.success, "failure2".failureNel, 3.success, "failure4".failureNel)

  val result: ValidationNel[String, Vector[Int]] = validations.sequenceU
  assert(result === NonEmptyList("failure2", "failure4").failure[Vector[Int]])

  println(validations.sequenceU)

  val onlyEvenAllowed: Int => ValidationNel[String, Int] = x =>
    if (x % 2 === 0) x.successNel else (x.toString + " is not even").failureNel

  val evens = IList(2, 4, 6, 8)
  val notAllEvens = List(1, 2, 3, 4)

  assert(evens.traverseU(onlyEvenAllowed) === IList(2, 4, 6, 8).success)
  assert(
    notAllEvens.traverseU(onlyEvenAllowed) === NonEmptyList(
      "1 is not even",
      "3 is not even").failure)

  import scalaz.State._

  // state stores the last seen Int, returns whether of not the current was a repeat
  val checkForRepeats: Int => State[Option[Int], Boolean] = { next =>
    for {
      last <- get
      _ <- put(some(next))
    } yield (last === some(next))
  }

  val nonRepeating = List(1, 2, 3, 4)
  val repeating = List(1, 2, 3, 3, 4)

  // traverse the lists with None as the starting state, we get back a
  // list of Booleans meaning "this element was a repeat of the
  // previous
  val res1: List[Boolean] = nonRepeating.traverseS(checkForRepeats).eval(None)
  val res2: List[Boolean] = repeating.traverseS(checkForRepeats).eval(None)

  assert(Tag.unwrap(res1.foldMap(Tags.Disjunction(_))) === false)
  assert(Tag.unwrap(res2.foldMap(Tags.Disjunction(_))) === true)
}

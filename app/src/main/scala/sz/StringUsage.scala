package sz

import scalaz._
import Scalaz._

object StringUsage extends App {

  import std.string._

  println(charsNel("foo").isDefined)
  println(charsNel("").isEmpty)

  import stringSyntax._

  assert("foo".charsNel.isDefined)
  assert("".charsNel.isEmpty)
}

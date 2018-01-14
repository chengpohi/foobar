/**
  * scala99
  * Created by chengpohi on 9/20/16.
  */
object TestFooBar extends App {
  private val UPPER_PATTERN = "^[A-Z].*".r
  private val LOWER_PATTERN = "^[a-z].*".r
  private val NUMBER_PATTERN = "^[0-9].*".r

  "00asdf" match {
    case UPPER_PATTERN() =>
      println("upp")
    case LOWER_PATTERN() =>
      println("lower")
    case NUMBER_PATTERN() =>
      println("number")
  }
}
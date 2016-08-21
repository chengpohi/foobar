package nlp

import org.scalatest.{FlatSpec, ShouldMatchers}

/**
  * scala99
  * Created by chengpohi on 8/18/16.
  */
class StringsTest extends FlatSpec with ShouldMatchers{
  import Strings._
  "editdistance" should "calc strings distance" in {
    val distance1: Int = "Hello" distance "Heloo"
    assert(distance1 === 1)
    val distance2: Int = "Heplo" distance "Heloo"
    assert(distance2 === 2)
    val distance3: Int = "" distance "Heloo"
    assert(distance3 === 5)
    val distance4: Int = "aasdksjdkfjaksdjfkjiasdfsdfasdfasdfasdf" distance "wqeruqwerkkjklasdiasdfasdfmnzxcvj"
    assert(distance4 === 22)
  }
}

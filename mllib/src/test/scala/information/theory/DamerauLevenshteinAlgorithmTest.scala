package information.theory

import org.scalatest.FlatSpec
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.matchers.ShouldMatchers._

/**
 * BookMark model
 * Created by chengpohi on 7/12/15.
 */
class DamerauLevenshteinAlgorithmTest extends FlatSpec {
  val strings =
    Table(
      ("n", "n", "d"),
      ("str1", "str2", 1),
      ("", "abcde", 5),
      ("abcd", "", 4),
      ("abc", "abcd", 1),
      ("acb", "abcd", 2),
      ("cab", "abcd", 3),
      ("badc", "abcd", 2)
    )

  "DamerauLevenshteinAlgorithm" should "calc the distance of str1, str2" in {
    forAll(strings) { (str1: String, str2: String, distance: Int) =>
      DamerauLevenshteinAlgorithm.calc(str1, str2) should equal(distance)
    }
  }
}

/**
  * scala99
  * Created by chengpohi on 9/20/16.
  */

import org.json4s._
import org.json4s.native.JsonMethods._

object TestFooBar extends App {
  var t: () => Int = _

  t = () => 1
  println(t())
  val json =
    """
      |{
      |    "id": "1",
      |    "details": [{
      |        "tax": [{
      |            "amount": 1
      |        },
      |        {
      |            "amount": 2
      |        }]
      |    }]
      |}
      |
  """.stripMargin

  val amounts = parse(json) \ "details" \ "tax" \ "amount"
  implicit val formats = DefaultFormats
  val decimals = amounts.extract[List[BigDecimal]]
  decimals.map(i => i.bigDecimal)
  println(decimals)
}

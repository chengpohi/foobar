import java.net.InetSocketAddress

import akka.http.scaladsl.model.RemoteAddress
import akka.http.scaladsl.model.headers.`Remote-Address`
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, WordSpec}


class TestControllerTest extends WordSpec with Matchers with ScalatestRouteTest {
  def myRoute: Route = {
    get {
      pathSingleSlash {
        extractClientIP { ip => {
          headerValueByName("Remote-Address") { res => {
            println(res)
            println(ip)
            complete("Hello World")
          }
          }
        }
        }
      }
    }
  }

  "The service" should {
    "return a greeting for GET requests to the root path" in {
      Get().withHeaders(
        `Remote-Address`(RemoteAddress(new InetSocketAddress("127.0.0.1", 23)))
      ) ~> myRoute ~> check {
        responseAs[String] shouldEqual "Hello World"
      }
    }
  }
}

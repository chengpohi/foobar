import java.io.{PrintStream, ByteArrayOutputStream}

import org.scalatest.{FunSuite, BeforeAndAfter}

/**
 * Created by chengpohi on 7/14/15.
 */

class RedirectOutputStream extends FunSuite with BeforeAndAfter {
  val outContent = new ByteArrayOutputStream()
  val errContent = new ByteArrayOutputStream()

  before {
    System.out.print()
    System.setOut(new PrintStream(outContent))
    System.setErr(new PrintStream(errContent))
  }

  test("Hello") {
    System.out.println("Hello World")
  }
}


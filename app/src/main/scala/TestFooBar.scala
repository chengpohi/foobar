

/**
  * scala99
  * Created by chengpohi on 9/20/16.
  */

class B(c: String => String) extends TestTmp((s: String) => c.apply(s))

object TestFooBar extends App {

}

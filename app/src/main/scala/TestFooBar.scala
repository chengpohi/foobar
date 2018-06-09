import org.json4s._
import org.json4s._
import org.json4s.native.JsonMethods._

import scala.collection.mutable


/**
  * scala99
  * Created by chengpohi on 9/20/16.
  */
object TestFooBar extends App {
  trait User {
    def name: String
  }

  trait DummyUser extends User {
    override def name: String = "foo"
  }

  trait Tweeter { self: User =>
    def tweet(msg: String) = println(s"$name: $msg")
  }

  val t = new Tweeter with User {
    override def name: String = ""
  }

}



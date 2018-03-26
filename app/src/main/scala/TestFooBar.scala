import scala.util.{Failure, Try}


/**
  * scala99
  * Created by chengpohi on 9/20/16.
  */
object TestFooBar extends App {
  def cleanUpAdvertiserInAdtech(name: String): Unit = {
    val attempt: Try[Int] = Try {
      throw new RuntimeException("")
    }
    attempt match {
      case Failure(e) => throw e
    }
  }
  cleanUpAdvertiserInAdtech("")
}
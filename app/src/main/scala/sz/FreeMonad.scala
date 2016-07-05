package sz

import sz.FreeMonad.{Request, _}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scalaz.std.list._
import scalaz.syntax.traverse._
import scalaz.{Free, Id, ~>}

/**
 * scala99
 * Created by chengpohi on 3/17/16.
 */
object FreeMonad {
  type UserId = Int
  type UserName = String
  type UserPhoto = String
  final case class Tweet(userId: UserId, msg: String)
  final case class User(id: UserId, name: UserName, photo: UserPhoto)

  sealed trait Service[A]

  final case class GetTweets(userId: UserId) extends Service[List[Tweet]]

  final case class GetUserName(userId: UserId) extends Service[UserName]

  final case class GetUserPhoto(userId: UserId) extends Service[UserPhoto]

  final case class Request[A](service: Service[A])

  def fetch[A](service: Service[A]): Free[Request, A] =
    Free.liftF[Request, A](Request(service): Request[A])
}

object TestInterpreter extends (Request ~> Id.Id) {
  import Id._

  override def apply[A](fa: Request[A]): Id[A] =  fa match {
    case Request(service) => service match {
      case GetTweets(userId) =>
        println(s"Get Tweets by userId $userId")
        List(Tweet(1, "hello world"), Tweet(2, "foo bar"))
      case GetUserName(userId) =>
        println(s"Get UserName $userId")
        userId match {
          case 1 => "name1"
          case 2 => "name2"
          case _ => "I don't know"
        }
      case GetUserPhoto(userId) =>
        println(s"Get Photo $userId")
        userId match {
          case 1 => "photo1"
          case 2 => "photo2"
          case _ => "photo3"
        }
    }
  }

}

object RunFreeMonad {
  def getUser(userId: UserId): Free[Request, FreeMonad.User] = for {
    name <- fetch(GetUserName(userId))
    photo <- fetch(GetUserPhoto(userId))
  } yield FreeMonad.User(userId, name, photo)

  def free(id: UserId): Free[Request, List[(String, FreeMonad.User)]] = for {
    tweets <- fetch(GetTweets(id))
    result <- tweets.map { tweet: Tweet =>
      for {
        user <- getUser(tweet.userId)
      } yield tweet.msg -> user
    }.sequenceU
  } yield result

  def main(args: Array[String]): Unit = {
    import scala.concurrent.ExecutionContext.Implicits.global
    Future {
      free(1).foldMap(TestInterpreter)
    }

    val t = Future {
      free(2).foldMap(TestInterpreter)
    }
    Await.result(t, Duration.Inf)
  }
}

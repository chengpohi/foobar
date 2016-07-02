package algorithm

/**
  * Default (Template) Project
  * Created by chengpohi on 7/2/16.
  */
trait UserMaker {
  def makeUser(item: String): User = {
    item.split(",") match {
      case Array(name, id) => User(name, id.trim.toInt)
    }
  }
}

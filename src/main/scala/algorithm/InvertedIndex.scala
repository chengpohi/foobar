package algorithm

import scala.collection.mutable

/**
  * Default (Template) Project
  * Created by chengpohi on 7/2/16.
  */
class InvertedIndex(val userMap: mutable.Map[String, User]) {
  def this() = this(new mutable.HashMap[String, User]())

  def tokenizeName(user: User): Seq[String] = user.name.split(" ").map(s => s.toLowerCase)

  def add(term: String, user: User): Unit = {
    userMap += term -> user
  }

  def add(user: User): Unit = {
    tokenizeName(user).foreach(t =>
      userMap.synchronized {
        add(t, user)
      }
    )
  }
}

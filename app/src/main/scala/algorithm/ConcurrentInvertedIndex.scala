package algorithm

import java.util.concurrent.ConcurrentHashMap

import scala.collection.concurrent

import scala.collection.JavaConverters._

/**
  * Default (Template) Project
  * Created by chengpohi on 7/2/16.
  */
class ConcurrentInvertedIndex(
    override val userMap: concurrent.Map[String, User])
    extends InvertedIndex(userMap) {
  def this() = this(new ConcurrentHashMap[String, User] asScala)
}

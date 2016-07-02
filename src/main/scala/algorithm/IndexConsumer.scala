package algorithm

import java.util.concurrent.BlockingQueue

/**
  * Default (Template) Project
  * Created by chengpohi on 7/2/16.
  */
class IndexConsumer(invertedIndex: InvertedIndex, queue: BlockingQueue[String]) extends Consumer[String](queue) with UserMaker {
  override def consume(item: String): Unit = invertedIndex.add(makeUser(item))
}

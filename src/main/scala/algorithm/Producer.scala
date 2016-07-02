package algorithm

import java.util.concurrent.BlockingQueue

import scala.io.Source

/**
  * Default (Template) Project
  * Created by chengpohi on 7/2/16.
  */
class Producer(path: String, queue: BlockingQueue[String]) extends Runnable {
  override def run(): Unit = {
    Source.fromFile(path).getLines().foreach(l =>
      queue.put(l)
    )
  }
}

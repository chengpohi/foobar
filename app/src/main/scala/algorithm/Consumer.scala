package algorithm

import java.util.concurrent.BlockingQueue

/**
  * Default (Template) Project
  * Created by chengpohi on 7/2/16.
  */
class Consumer[T](queue: BlockingQueue[T]) extends Runnable {

  def consume(item: T): Unit = ???

  override def run(): Unit = {
    while (true) {
      val item = queue.take()
      consume(item)
    }
  }
}

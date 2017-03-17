package concurrent

import java.util.concurrent.{BlockingQueue, LinkedBlockingDeque, TimeUnit}

/**
  * scala99
  * Created by chengpohi on 4/15/16.
  */
object App {
  def main(args: Array[String]): Unit = {
    runProducerAndConsumer()
  }

  def runProducerAndConsumer() = {
    val blockingDeque: LinkedBlockingDeque[String] =
      new LinkedBlockingDeque[String](20)
    new Thread(new Producer(blockingDeque)).start()
    for (i <- 1 to 10) {
      new Thread(new Consumer(blockingDeque)).start()
    }
  }
}

class Producer(q: BlockingQueue[String]) extends Runnable {
  override def run(): Unit = {
    while (true) {
      println("I am putting, size: " + q.size())
      q.put("hello")
    }
  }
}

class Consumer(q: BlockingQueue[String]) extends Runnable {
  override def run(): Unit = {
    while (true) {
      val take: String = q.take()
      println(
        take + " size: " + q.size() + " " + Thread.currentThread().getName)
      TimeUnit.SECONDS.sleep(2)
    }
  }
}

package algorithm

import java.util.concurrent.{Executors, LinkedBlockingDeque}

/**
  * Default (Template) Project
  * Created by chengpohi on 7/2/16.
  */
object App {
  def main(args: Array[String]) {
    val index = new ConcurrentInvertedIndex()
    val queue = new LinkedBlockingDeque[String]()
    val producer = new Producer("users.txt", queue)
    new Thread(producer).start()

    val core = 4
    val pool = Executors.newFixedThreadPool(4)
    Range.apply(0, core).foreach(t => pool.submit(new IndexConsumer(index, queue)))

    val searcher = new SearchEngine(index)
    new Thread(searcher).start()
  }
}

package algorithm

import scala.io.StdIn

/**
  * Default (Template) Project
  * Created by chengpohi on 7/2/16.
  */
class SearchEngine(index: InvertedIndex) extends Runnable {
  override def run(): Unit = {
    while (true) {
      println("Input User Name:")
      val input = StdIn.readLine()
      println(
        index.userMap.getOrElse(input.trim.toLowerCase, "Sorry Not Found"))
    }
  }
}

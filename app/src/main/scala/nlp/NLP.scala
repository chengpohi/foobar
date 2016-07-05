package nlp

import scala.io.Source

/**
  * scala99
  * Created by chengpohi on 7/5/16.
  */
object NLP {
  def main(args: Array[String]): Unit = {
    val res = Source.fromURL(getClass.getResource("/stopwords.txt")).getLines()
  }
}

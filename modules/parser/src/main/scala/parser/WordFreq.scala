package parser

/**
  * scala-parser-combinator
  * Created by chengpohi on 11/26/15.
  */
case class WordFreq(word: String, count: Int) {
  override def toString =
    "Word <" + word + "> " +
      "occurs with frequency " + count
}

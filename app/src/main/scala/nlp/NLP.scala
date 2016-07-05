package nlp

import scalaz.Free

object NLP {

  import AnalyzerMonad._

  def free(url: String): Free[Request, AnalyzerMonad.AnalyzedDoc] = for {
    webdoc <- fetch(GetDoc(url))
    result <- fetch(TokenizeWords(webdoc))
  } yield result

  def main(args: Array[String]): Unit = {
    val f = free("https://msdn.microsoft.com/en-us/library/ee658124.aspx").foldMap(AnalyzerInterpreter)
    f.words.take(10).foreach(println)
    println("-"*20)
    f.stopWords.take(10).foreach(println)
  }
}

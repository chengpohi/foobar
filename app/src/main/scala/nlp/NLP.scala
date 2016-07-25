package nlp

import scala.collection.parallel.ParSeq
import scalaz.Free

object NLP {

  import AnalyzerMonad._

  def get(url: String): Free[Request, AnalyzerMonad.AnalyzedDoc] = for {
    webdoc <- fetch(GetDoc(url))
    result <- fetch(TokenizeWords(webdoc))
  } yield result

  def crawl(urls: List[String]): ParSeq[AnalyzedDoc] = urls.par.map(s => crawl(s))
  def crawl(s: String) = get(s).foldMap(AnalyzerInterpreter)

  def main(args: Array[String]): Unit = {
    val seeds = List(
      "https://msdn.microsoft.com/en-us/library/ee658098.aspx",
      "https://msdn.microsoft.com/en-us/library/ee658124.aspx",
      "https://msdn.microsoft.com/en-us/library/ee658117.aspx",
      "https://msdn.microsoft.com/en-us/library/ee658084.aspx"
    )
    val docs: ParSeq[AnalyzedDoc] = crawl(seeds)
    println(docs.head tf "design")
  }

}

package nlp

import nlp.AnalyzerMonad.AnalyzedDoc

import scala.collection.parallel.ParSeq
import scalaz.Free

/**
  * scala99
  * Created by chengpohi on 7/25/16.
  */
class Crawler {

  import AnalyzerMonad._

  def get(url: String): Free[Request, AnalyzerMonad.AnalyzedDoc] = for {
    webdoc <- fetch(GetDoc(url))
    result <- fetch(TokenizeWords(webdoc))
  } yield result

  def crawl(urls: List[String]): DocSet = DocSet(urls.par.map(s => crawl(s)))
  def crawl(s: String) = get(s).foldMap(AnalyzerInterpreter)
}

case class DocSet(docs: ParSeq[AnalyzedDoc])

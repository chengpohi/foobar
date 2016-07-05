package nlp

import nlp.AnalyzerMonad.{AnalyzedDoc, GetDoc, Request, TokenizeWords, WebDoc, WordTerm}
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import scala.io.Source
import scalaz.{Free, Id, ~>}

case object connect {
  def to(url: String) = Jsoup.connect(url).get
}

object AnalyzerMonad {
  final case class WebDoc(url: String, doc: Document) {
    def text: String = doc.text()
  }
  final case class WordTerm(word: String, frequency: Int)
  final case class AnalyzedDoc(words: List[WordTerm], stopWords: List[WordTerm], size: Int)
  sealed trait Service[A]
  case class GetDoc(url: String) extends Service[WebDoc]
  case class TokenizeWords(doc: WebDoc) extends Service[AnalyzedDoc]
  final case class Request[A](service: Service[A])
  def fetch[A](service: Service[A]): Free[Request, A] =
    Free.liftF[Request, A](Request(service): Request[A])
}

object WordTokenizer {
  val WORD_PATTERN = "\\w+".r
  val STOP_WORDS = Source.fromURL(getClass.getResource("/stopwords.txt")).getLines().toSet
  def apply(text: String): (List[String], List[String], Int) = {
    val w = WORD_PATTERN.findAllIn(text).matchData.map(i => i.group(0).trim.toLowerCase).toList
    (w filterNot STOP_WORDS, w filter STOP_WORDS, w.size)
  }
}

object AnalyzerInterpreter extends (Request ~> Id.Id) {
  def toWordTerm(ws: List[String]) = {
    ws.groupBy(i => i).mapValues(_.size).toList.sortWith(_._2 > _._2).map(WordTerm.tupled)
  }

  override def apply[A](fa: Request[A]): Id.Id[A] = fa match {
    case Request(service) => service match {
      case GetDoc(url) =>
        WebDoc(url, connect to url)
      case TokenizeWords(doc: WebDoc) =>
        val ws  = WordTokenizer(doc.text)
        val words = toWordTerm(ws._1)
        val stopWords = toWordTerm(ws._2)
        AnalyzedDoc(words, stopWords, ws._3)
    }
  }
}


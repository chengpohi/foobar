package nlp

import nlp.AnalyzerMonad.AnalyzedDoc

import scala.util.Try

/**
  * scala99
  * Created by chengpohi on 7/26/16.
  */
object DocSetAlgorithm {
  implicit class IDF(docSet: DocSet) {
    def idf(word: String) = {
      val dn = docSet.count(word).toDouble
      Try(math.log(docSet.size / dn.toDouble)).toOption
    }
    def tfIdf(word: String) = {
      import AnalyzedDocAlgorithm.TF
      val id = idf(word)
      docSet.map(doc => for {
        t <- doc.tf(word)
        i <- id
      } yield (doc, t * i)).flatten.toList.sortWith(_._2 > _._2)
    }
  }
}

object AnalyzedDocAlgorithm {
  implicit class TF(analyzedDoc: AnalyzedDoc) {
    def tf(word: String) =
      analyzedDoc.words.find(w => w.word == word).map(t => t.frequency / analyzedDoc.size.toDouble)
  }
}

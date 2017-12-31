package nlp

import java.io.FileWriter
import java.nio.file.{Files, Paths}
import java.util.stream
import java.util.stream.Collectors

import org.json4s._
import org.json4s.native.JsonMethods._

case class Question(qid: String, question: List[Int], utterance: List[Int], label: List[Int])

object DataExtractor extends App {
  implicit val format = org.json4s.DefaultFormats

  println("load words...")
  val content: String = Files.lines(Paths.get("/Users/xiachen/IdeaProjects/insuranceqa-corpus-zh/corpus/pairs/iqa.vocab.json")).collect(Collectors.joining())
  val doc = parse(content)
  val word2id = (doc \ "word2id").extract[Map[String, Int]]
  val id2word = (doc \ "id2word").extract[Map[Int, String]]

  outputKeyWords(word2id)

  println("load questions...")
  val questions: stream.Stream[String] = Files.lines(Paths.get("/Users/xiachen/IdeaProjects/insuranceqa-corpus-zh/corpus/pairs/iqa.train.json"))

  outputQuestion(questions, id2word)

  def outputQuestion(qs: stream.Stream[String], id2word: Map[Int, String]): Unit = {
    val writer = new FileWriter("questions.txt")
    qs.filter(s => !s.startsWith("[") && !s.startsWith(",") && !s.startsWith("]"))
      .forEach(s => {
      val q = parse(s).extract[Question]
        if (q.label == List(1, 0)) {
          val question = q.question.map(i => id2word(i)).mkString("")
          val answer = q.utterance.map(i => id2word(i)).mkString("")
          writer.write(question + System.lineSeparator())
          writer.write(answer + System.lineSeparator())
          writer.write("===" + System.lineSeparator())
        }
    })
  }

  def outputKeyWords(word2id: Map[String, Int]): Unit = {
    val writer = new FileWriter("userdict.txt")
    for (elem <- word2id.keys) {
      writer.write(elem + System.lineSeparator())
    }
  }

}

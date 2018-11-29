package sb

import scalaz._
import Scalaz._


trait Block {
  type Blocked[A] = Writer[DList[String], A]
  def block(message: String): Blocked[Unit] = DList(message).tell

  type FUN = () => Any
  var blocks: Array[Blocked[FUN]] = Array()

  def gen: Unit = {
    blocks.foreach(i => {
      val res = i.run
      res._1.toList.foreach(println)
      res._2.apply()
    })
  }
}

trait HeaderBlock extends Block {
  val header: String

  def in(content: => Any): Unit = {
    blocks = Array(block(header + " in:") as (content _))
    gen
  }
}

object SBook {

  case class title(title: String)

  implicit def titleToBlock(h: String): HeaderBlock = {
    new HeaderBlock {
      override val header: String = h
    }
  }
}

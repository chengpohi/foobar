package parser

/**
  * Default (Template) Project
  * Created by chengpohi on 3/20/16.
  */
case class StringLiteral(s: String) extends Literal {
  val literal = s.substring(1, s.length - 1)

  //TODO: apply missing escapes
  def doubleValue = literal.toDouble

  def boolValue = if (literal.toLowerCase == "false") false else true

  def stringValue = literal

  override def toString = s

  override def env(env: Map[String, Literal]): Literal = ???
}

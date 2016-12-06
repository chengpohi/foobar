package parser

/**
  * Default (Template) Project
  * Created by chengpohi on 3/20/16.
  */
case class BooleanLiteral(literal: Boolean) extends Literal {
  def doubleValue = if (literal) 1.0 else 0.0

  def boolValue = literal

  def stringValue = literal.toString

  override def toString = literal.toString

  override def env(env: Map[String, Literal]): Literal = ???
}

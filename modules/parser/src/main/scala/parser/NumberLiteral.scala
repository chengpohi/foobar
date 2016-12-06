package parser

/**
  * Default (Template) Project
  * Created by chengpohi on 3/20/16.
  */
case class NumberLiteral(literal: Double) extends Literal {
  def doubleValue = literal

  def boolValue = literal != 0.0

  def stringValue = literal.toString

  override def toString = literal.toString

  override def env(env: Map[String, Literal]): Literal = ???
}

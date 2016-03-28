package parser

/**
  * Default (Template) Project
  * Created by chengpohi on 3/20/16.
  */
trait Literal extends Expression {
  def eval(env: Map[String, Literal]) = this

  def doubleValue: Double

  def boolValue: Boolean

  def stringValue: String
}

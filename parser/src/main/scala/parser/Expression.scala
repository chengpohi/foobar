package parser

/**
 * scala-parser-combinator
 * Created by chengpohi on 10/4/15.
 */
trait Expression {
  def env(env: Map[String, Literal]): Literal
}

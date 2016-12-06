package parser

/**
  * Default (Template) Project
  * Created by chengpohi on 3/20/16.
  */
case class Variable(name: String) extends Expression {
  def eval(env: Map[String, Literal]) = env(name)

  override def toString = name

  override def env(env: Map[String, Literal]): Literal = ???
}

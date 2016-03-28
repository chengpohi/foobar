package parser

/**
  * Default (Template) Project
  * Created by chengpohi on 3/20/16.
  */
case class NamedFunction[T, V](f: T => V, name: String) extends (T => V) {
  def apply(t: T) = f(t)
  override def toString() = name
}

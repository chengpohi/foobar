package mcs

import scala.language.experimental.macros
import scala.language.dynamics
/**
  * Default (Template) Project
  * Created by chengpohi on 7/2/16.
  */
object CaseClassToMap {

  implicit class Mappable[T <: Model](val model: T) {
    def toMap: Map[String, Any] = macro Macros.toMapImpl[T]
  }

  private object Macros {
    import scala.reflect.macros.blackbox.Context

    def toMapImpl[T: c.WeakTypeTag](c: Context): c.Expr[Map[String, Any]] = {
      import c.universe._
      val model = Select(c.prefix.tree, TermName("model"))
      val pairs = weakTypeOf[T].decls.collect {
        case m: MethodSymbol if m.isCaseAccessor =>
          val name = m.asTerm.name
          val mapKey = name.decodedName.toString
          val value = c.Expr[Any](Select(model, m.name))
          q"$mapKey -> $value"
      }
      c.Expr[Map[String, Any]] {
        q"""
          Map(..$pairs)
      """
      }
    }
  }
}

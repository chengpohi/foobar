import scala.language.experimental.macros
/**
  * scala99
  * Created by chengpohi on 3/28/16.
  */
abstract class Model {
  def toMap[T]: Map[String, Any] = macro CaseClassToMap.toMapImpl[T]
}
object CaseClassToMap {
  import scala.reflect.macros.blackbox.Context

  def toMapImpl[T: c.WeakTypeTag](c: Context): c.Expr[Map[String, Any]] = {
    import c.universe._
    val pairs = weakTypeOf[T].decls.collect {
      case m: MethodSymbol if m.isCaseAccessor =>
        val name = m.asTerm.name
        val mapKey = name.decodedName.toString
        val value = c.Expr[Any](Select(c.untypecheck(c.prefix.tree), m.name))
        q"$mapKey -> $value"
    }
    c.Expr[Map[String, Any]]{
      q"""
          Map(..$pairs)
      """
    }
  }
}

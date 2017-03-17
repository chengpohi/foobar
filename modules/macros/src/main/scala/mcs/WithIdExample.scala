package mcs

import scala.language.dynamics
import scala.language.experimental.macros

/**
  * scala99
  * Created by chengpohi on 3/28/16.
  */
object WithIdExample {
  import scala.reflect.macros.blackbox.Context
  def withId[T, I](entity: T, id: I): T = macro withIdImpl[T, I]

  def withIdImpl[T: c.WeakTypeTag, I: c.WeakTypeTag](
      c: Context)(entity: c.Expr[T], id: c.Expr[I]): c.Expr[T] = {
    import c.universe._
    val tree = reify(entity.splice).tree
    val copy = entity.actualType.member(TermName("copy"))
    copy match {
      case s: MethodSymbol
          if s.paramLists.flatten.map(_.name).contains(TermName("id")) =>
        c.Expr[T] {
          Apply(
            Select(tree, copy),
            AssignOrNamedArg(Ident(TermName("id")), reify(id.splice).tree) :: Nil)
        }
      case _ => c.abort(c.enclosingPosition, "No eligible copy method")
    }
  }
}

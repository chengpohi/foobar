package meta

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.util.Try

/**
  * Created by xiachen on 14/11/2016.
  */

import scala.meta._

@compileTimeOnly("@RetryOnFailure not expanded")
class AP(str: String) extends StaticAnnotation {
  inline def apply(defn: Any): Any = meta {
    defn match {
      case q"..$mods def $name[..$tparams](...$paramss): String" =>
        val q"new $_($arg)" = this
        val t = Try(arg.toString).getOrElse("")
        q"""
          ..$mods def $name[..$tparams](...$paramss): String = {
            "%s".format(${paramss.toString})
          }
        """
      case _ => abort("@Benchmark can be annotation of method only")
    }
  }
}

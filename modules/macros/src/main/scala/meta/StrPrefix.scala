package meta


import scala.annotation.StaticAnnotation

/**
  * Created by xiachen on 14/11/2016.
  */

import scala.meta._

class StrPrefix extends StaticAnnotation {
  inline def apply(defn: Any): Any = meta {
    defn match {
      case q"..$mods def $name[..$tparams](...$paramss): String = $expr" =>
        q"""
          ..$mods def $name[..$tparams](...$paramss): String = {

          "Foo Bar " + $expr
          }
        """
      case _ => abort("@Benchmark can be annotation of method only")
    }
  }
}

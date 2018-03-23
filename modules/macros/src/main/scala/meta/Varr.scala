package meta


/**
  * Created by xiachen on 14/11/2016.
  */

import scala.annotation.StaticAnnotation
import scala.meta._

class Varr extends StaticAnnotation {
  inline def apply(defn: Any): Any = meta {
    defn match {
      case q"val $name = Vector(..$paramss)" => {
        val stats = paramss.map {
          case i: Lit =>
            s"""val ${i.value} = "test"""".parse[Stat].get
        }
        q"""
            ..$stats
         """
      }
      case _ => abort("@Benchmark can be annotation of method only")
    }
  }
}

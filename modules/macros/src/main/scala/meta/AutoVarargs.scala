package meta

import scala.annotation.StaticAnnotation

/**
  * Created by xiachen on 14/11/2016.
  */

import scala.meta._

class AutoVarargs extends StaticAnnotation {
  inline def apply(defn: Any): Any = meta {
    defn match {
      case q"def add(...$paramss): Int = $expr" =>
        q"""
          def add(i: Int*): Int = {
            i.sum
          }
        """
    }
  }
}

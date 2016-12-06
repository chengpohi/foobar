package meta

import scala.annotation.{StaticAnnotation, compileTimeOnly}

/**
  * Created by xiachen on 14/11/2016.
  */

import scala.meta._

@compileTimeOnly("@main not expanded")
class main extends StaticAnnotation {
  inline def apply(defn: Any): Any = meta {
    val q"object $name { ..$stats }" = defn
    val main =
      q"""
def main(args: Array[String]): Unit = { ..$stats }
"""
    q"object $name { $main }"
  }
}


package meta

import scala.annotation.StaticAnnotation

/**
  * Created by xiachen on 14/11/2016.
  */

import scala.meta._

class main extends StaticAnnotation {
  inline def apply(defn: Any) = meta {
    val q"object $name { ..$stats }" = defn
    val main =
      q"""
def main(args: Array[String]): Unit = { ..$stats }
"""
    q"object $name { $main }"
  }
}

@main
object MetaMacro {

}

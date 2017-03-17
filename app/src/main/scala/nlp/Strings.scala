package nlp

import breeze.linalg.min

/**
  * scala99
  * Created by chengpohi on 8/18/16.
  */
object Strings {
  implicit class Str(a: String) {
    def distance(b: String): Int = {
      a.foldLeft((0 to b.length).toList)((prev, x) =>
          (prev zip prev.tail zip b).scanLeft(prev.head + 1) {
          case (h, ((d, v), y)) => {
            min(min(h + 1, v + 1), d + (if (x == y) 0 else 1))
          }
      }) last
    }
  }
}

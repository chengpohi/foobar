package nlp

/**
  * scala99
  * Created by chengpohi on 8/18/16.
  */
object Strings {
  implicit class Str(s: String) {
    def distance(word2: String): Int = {
      val len1: Int = s.length
      val len2: Int = word2.length
      if (len1 == 0) return len2
      if (len2 == 0) return len1
      val d = Array.fill(len1 + 1, len2 + 1)(0)
      (1 to len1).foreach(i => {
        (1 to len2).foreach(j => {
          var cos = 0
          if (s.charAt(i - 1) == word2.charAt(j - 1))
            cos = 0
          else
            cos = 1
          d(i)(j) = List(
            d(i - 1)(j) + 1,
            d(i)(j - 1) + 1,
            d(i - 1)(j - 1) + cos
          ).min
        })
      })
      s.toCharArray
      d(len1)(len2)
    }
  }
}

package nlp

/**
  * scala99
  * Created by chengpohi on 8/18/16.
  */
object Strings {
  implicit class Str(s: String) {
    def distance(word2: String): Int = {
      def ed(word1: String, len1: Int, word2: String, len2: Int): Int = {
        if (len1 == 0)
          return len2
        if (len2 == 0)
          return 1
        var cos = 0
        if (word1.charAt(len1 - 1) == word2.charAt(len2 - 1))
          cos = 0
        else
          cos = 1
        List(ed(word1, len1 - 1, word2, len2) + 1,
          ed(word1, len1, word2, len2 - 1) + 1,
          ed(word1, len1 - 1, word2, len2 - 1) + cos).min
      }
      ed(s, s.length, word2, word2.length)
    }
  }
}

package information.theory

/**
  * Metric for transform a string to another string
  * include four operations:
  * - insertion
  * - deletion
  * - subsitution of a single character
  * - transposition of two adjacent characters.
  *
  * Created by chengpohi on 7/8/15.
  */
object DamerauLevenshteinAlgorithm {
  def calc(str1: String, str2: String): Int = {
    val str1Length: Int = str1.length
    val str2Length: Int = str2.length

    if (str1Length == 0 || str2Length == 0)
      Math.max(str1Length, str2Length)
    else {
      val a = Array.ofDim[Int](str1Length, str2Length)
      for (i <- 0 until str1Length)
        a(i)(0) = i
      for (j <- 0 until str2Length)
        a(0)(j) = j

      for (i <- 1 until str1Length) {
        for (j <- 1 until str2Length) {
          val cost = if (str1.charAt(i) == str2.charAt(j)) 0 else 1

          a(i)(j) = Math.min(Math.min(a(i - 1)(j) + 1, a(i)(j - 1) + 1),
                             a(i - 1)(j - 1) + cost)

          if (i > 1 && j > 1 && str1.charAt(i) == str2.charAt(j - 1) && str1
                .charAt(i - 1) == str2.charAt(j)) {
            a(i)(j) = Math.min(a(i)(j), a(i - 2)(j - 2) + cost)
          }

        }
      }
      a(str1Length - 1)(str2Length - 1)
    }
  }

  def main(args: Array[String]): Unit = {
    println(calc("str1", "str23"))
    println(calc("", "str23"))
  }
}

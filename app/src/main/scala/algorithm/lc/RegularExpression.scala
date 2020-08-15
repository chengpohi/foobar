package algorithm.lc

object RegularExpression {


  def isMatch(s: String, p: String): Boolean = {
    var memo = Array.fill(s.length + 1)(false)
    helper(s.toCharArray, p.toCharArray, s.length - 1, p.length - 1, memo)
  }

  def helper(s: Array[Char], p: Array[Char], i: Int, j: Int, memo: Array[Boolean]): Boolean = {
    if (memo(i + 1))
      return true

    if (i == -1 && j == -1) {
      memo(i + 1) = true
      return true
    }

    var isFirstMatching = false

    if (i >= 0 && j >= 0 && (s(i) == p(j) || p(j) == '.' ||
      (p(j) == '*' && (p(j - 1) == s(i) || p(j - 1) == '.')))) {
      isFirstMatching = true
    }

    if (j >= 1 && p(j) == '*') {
      val zero = helper(s, p, i, j - 2, memo)
      val m = isFirstMatching && helper(s, p, i - 1, j, memo)

      if (m || zero) {
        memo(i + 1) = true
      }

      memo(i + 1)
    }

    if (isFirstMatching && helper(s, p, i - 1, j - 1, memo)) {
      memo(i + 1) = true
    }

    memo(i + 1)
  }

  def main(args: Array[String]): Unit = {
    println(isMatch("aa", "a"))
    println(isMatch("aa", "a*"))
  }
}

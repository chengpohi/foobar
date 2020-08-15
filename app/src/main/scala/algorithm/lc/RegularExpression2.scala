package algorithm.lc

object RegularExpression2 {


  def isMatch(s: String, p: String): Boolean = {
    if (s.equals(p)) {
      return true
    }

    val sc = s.toCharArray
    val pc = p.toCharArray
    val m = s.length
    val n = p.length
    val dp = Array.fill(m + 1, n + 1)(false)

    dp(0)(0) = true

    (0 to m).foreach(i => {
      (1 to n).foreach(j => {
        if (pc(j - 1) == '*') {
          dp(i)(j) = dp(i)(j - 2)
          if (matches(sc, pc, i, j - 1)) {
            dp(i)(j) = dp(i)(j) || dp(i - 1)(j)
          }
        } else {
          if (matches(sc, pc, i, j)) {
            dp(i)(j) = dp(i - 1)(j - 1)
          }
        }
      })
    })

    dp(m)(n)
  }

  def matches(sc: Array[Char], pc: Array[Char], i: Int, j: Int): Boolean = {
    if (i == 0) {
      return false
    }

    if (pc(j - 1) == '.') {
      return true
    }

    sc(i - 1) == pc(j - 1)
  }


  def main(args: Array[String]): Unit = {
    println(isMatch("aa", "a"))
    println(isMatch("aa", "a*"))
  }
}

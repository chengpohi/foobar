package algorithm.lc

object LongestValidParentheses {
  def longestValidParentheses(s: String): Int = {
    val dp = Array.fill(s.length)(0)
    var maxLen = 0
    (1 until s.length).foreach(i => {
      if (s.charAt(i) == ')') {
        if (s.charAt(i - 1) == '(') {
          i match {
            case j if j >= 2 => dp(i) = dp(i - 2) + 2
            case _ => dp(i) = 2
          }
        } else if (i - dp(i - 1) > 0 && s.charAt(i - dp(i - 1) - 1) == '(') {
          if ((i - dp(i - 1)) >= 2) {
            dp(i) = dp(i - 1) + dp(i - dp(i - 1) - 2) + 2
          } else {
            dp(i) = dp(i - 1) + 2
          }
        }
        maxLen = Math.max(maxLen, dp(i))
      }
    })
    maxLen
  }

  def main(args: Array[String]): Unit = {
//    println(longestValidParentheses("(()"))
//    println(longestValidParentheses("(()"))
    println(longestValidParentheses("(())"))
  }

}

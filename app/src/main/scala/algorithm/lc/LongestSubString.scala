package algorithm.lc

object LongestSubString {
  def lengthOfLongestSubstring(s: String): Int = {
    var freg = Array.fill(256)(0)
    var r = -1
    var l = 0
    var res = 0

    while (l < s.length) {
      if (r + 1 < s.length && freg(s.charAt(r + 1)) == 0) {
        freg(s.charAt(r + 1)) = freg(s.charAt(r + 1)) + 1
        r = r + 1
      } else {
        freg(s.charAt(l)) = freg(s.charAt(l)) - 1
        l = l + 1
      }

      res = Math.max(res, r - l + 1)
    }
    res
  }

  def main(args: Array[String]): Unit = {
    println(lengthOfLongestSubstring("abcabcbb"))
  }
}

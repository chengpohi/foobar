package algorithm.lc

import scala.collection.mutable.ListBuffer

object GenerateParenthesis {

  def generateParenthesis(n: Int): List[String] = {
    var l = ListBuffer[String]()
    backtrack(l, "", 0, 0, n)
    l.toList
  }

  def backtrack(l: ListBuffer[String], s: String, open: Int, close: Int, max: Int) {
    if (s.length == max * 2) {
      l += s.toString
      return
    }

    if (open < max) {
      backtrack(l, s + "(", open + 1, close, max)
    }

    if (close < open) {
      backtrack(l, s + ")", open, close + 1, max)
    }
  }


  def main(args: Array[String]): Unit = {
    println(generateParenthesis(3))
  }

}

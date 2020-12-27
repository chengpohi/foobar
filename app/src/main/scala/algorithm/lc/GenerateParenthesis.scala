package algorithm.lc

import scala.collection.mutable.ListBuffer

/**
 * 既然左括号的数量必须大于右括号的数量
 */
object GenerateParenthesis {

  def generateParenthesis(n: Int): List[String] = {
    val list = ListBuffer[String]()
    backtrack(list, "", 0, 0, n)

    list.toList

  }
  def backtrack(list: ListBuffer[String], str: String, left: Int, right: Int, n: Int): Unit = {
    if (str.length == n * 2) {
      list.append(str)
      return
    }

    if (left < n) {
      backtrack(list, str + "(", left + 1, right, n)
    }

    if (right < left) {
      backtrack(list, str + ")", left, right + 1, n)
    }
  }



  def main(args: Array[String]): Unit = {
    println(generateParenthesis(3))
  }

}

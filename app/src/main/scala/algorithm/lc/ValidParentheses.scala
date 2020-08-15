package algorithm.lc

import scala.collection.mutable

object ValidParentheses {
  def isValid(s: String): Boolean = {
    val ss = mutable.Stack[Char]()

    s.toCharArray.foreach {
      case '(' => ss.push(')')
      case '{' => ss.push('}')
      case '[' => ss.push(']')
      case a =>
        if (ss.isEmpty || a != ss.pop()) {
          return false
        }
    }

    if (ss.nonEmpty) {
      return false
    }
    true
  }

  def main(args: Array[String]): Unit = {
    println(isValid("(("))
  }
}

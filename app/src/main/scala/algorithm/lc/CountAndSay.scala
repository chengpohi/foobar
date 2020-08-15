package algorithm.lc

object CountAndSay {
  def countAndSay(n: Int): String = {
    if (n == 1) {
      return "1"
    }

    val str = countAndSay(n - 1)


    var count = 1
    var res = ""
    (0 until str.length).foreach(i => {
      if (i + 1 < str.length && str.charAt(i) == str.charAt(i + 1)) {
        count = count + 1
      } else {
        res = res + count.toString + str.charAt(i).toString
        count = 1
      }
    })

    res
  }

  def main(args: Array[String]): Unit = {
    println(countAndSay(2))
  }

}

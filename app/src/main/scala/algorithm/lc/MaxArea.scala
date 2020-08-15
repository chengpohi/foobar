package algorithm.lc

object MaxArea {
  def maxArea(height: Array[Int]): Int = {
    var i = 0
    var j = height.length - 1
    var res = 0

    while (i < j) {
      if (height(i) < height(j)) {
        res = Math.max((j - i) * height(i), res)
        i = i + 1
      } else {
        res = Math.max((j - i) * height(j), res)
        j = j - 1
      }
    }
    res
  }

  def main(args: Array[String]): Unit = {
    val res = maxArea(Array(1, 8, 6, 2, 5, 4, 8, 3, 7))

    println(res)
  }

}

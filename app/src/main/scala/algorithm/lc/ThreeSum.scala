package algorithm.lc

object ThreeSum {
  def threeSum(nums: Array[Int]): List[List[Int]] = {
    var list = List[List[Int]]()
    if (nums.isEmpty) {
      return list;
    }
    val num = nums.sorted
    num.indices.takeWhile(i => num(i) <= 0).filter(i => {
      !(i > 0 && num(i) == num(i - 1))
    }).foreach(k => {
      val target = 0 - num(k)
      var i = k + 1
      var j = num.length - 1

      while (i < j) {
        if (num(i) + num(j) == target) {
          list = list :+ List(num(k), num(i), num(j))
          while (i < j && num(i) == num(i + 1)) {
            i = i + 1
          }
          while (i < j && num(j) == num(j - 1)) {
            j = j - 1
          }
          i = i + 1
          j = j - 1
        } else if (num(i) + num(j) < target) {
          i = i + 1
        } else {
          j = j - 1
        }
      }
    })
    list
  }

  def main(args: Array[String]): Unit = {

    println(threeSum(Array(-1, 0, 1, 2, -1, -4)))
    println(threeSum(Array(0, 0, 0, 0)))
  }
}

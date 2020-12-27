package algorithm.lc

import scala.collection.mutable

object TwoSum {

  def twoSum(nums: Array[Int], target: Int): Array[Int] = {
    val mm = mutable.Map[Int, Int]()

    nums.zipWithIndex.foreach(p => {
      val (v, j) = (p._1, p._2)
      val k = target - v

      mm.get(k) match {
        case Some(i) =>
          return Array(j, i)
        case None =>
          mm(v) = j
      }
    })

    Array()
  }

  def main(args: Array[String]): Unit = {
    twoSum(Array(1, 2, 3, 5), 5).foreach(i => {
      println(i)
    })
  }
}

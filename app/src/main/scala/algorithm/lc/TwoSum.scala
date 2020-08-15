package algorithm.lc

object TwoSum {

  def twoSum(nums: Array[Int], target: Int): Array[Int] = {
    val mm = scala.collection.mutable.Map[Int, Int]()

    nums.zipWithIndex.foreach(p => {
      val (k, i) = (p._1, p._2)
      val r = target - k
      mm.get(r) match {
        case None =>
          mm(k) = i
        case Some(t) =>
          return Array(t, i)
      }
    })
    Array()
  }
}

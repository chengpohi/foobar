package algorithm.lc

object MedianSortedArrays {


  def findMedianSortedArrays(nums1: Array[Int], nums2: Array[Int]): Double = {
    val n = nums1.length
    val m = nums2.length

    val left = (n + m + 1) / 2
    val right = (n + m + 2) / 2

    (getKth(nums1, 0, n - 1, nums2, 0, m - 1, left) +
      getKth(nums1, 0, n - 1, nums2, 0, m - 1, right)) / 2.0

  }

  def getKth(nums1: Array[Int], start1: Int, end1: Int, nums2: Array[Int], start2: Int, end2: Int, k: Int): Double = {
    val len1 = end1 - start1 + 1
    val len2 = end2 - start2 + 1
    if (len1 > len2)
      return getKth(nums2, start2, end2, nums1, start1, end1, k)

    if (len1 == 0) {
      return nums2(start2 + k - 1)
    }

    if (k == 1) {
      return Math.min(nums1(start1), nums2(start2))
    }

    val i = start1 + Math.min(len1, k / 2) - 1
    val j = start2 + Math.min(len2, k / 2) - 1

    if (nums1(i) > nums2(j)) {
      return getKth(nums1, start1, end1, nums2, j + 1, end2, k - (j - start2 + 1))
    } else {
      return getKth(nums1, i + 1, end1, nums2, start2, end2, k - (i - start1 + 1))
    }
  }

  def main(args: Array[String]): Unit = {

    println(findMedianSortedArrays(Array(1, 1, 3, 3), Array(1, 1, 3, 3)))
  }

}

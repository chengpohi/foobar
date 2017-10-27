package learn.mllib

/**
  * mllib
  * Created by chengpohi on 8/18/15.
  */
object Algorithm {
  //get the mean of numbers
  def mean[T](x: Array[T])(implicit num: Numeric[T]): Double = {
    import num._
    x.sum.toDouble / x.length
  }

  //sort array to get the median number in array, if the array's length is even, get the median 2 numbers to sum
  // and divide by 2
  def median[T](x: Array[T])(implicit num: Numeric[T]): Double = {
    import num._
    val r = x.sorted
    val length: Int = x.length

    length / 2 match {
      case t: Int if t != 0 => (r(length / 2) + r(length / 2 - 1)).toDouble / 2
      case 0                => r(length / 2).toDouble
    }
  }

  def mode[T](x: Array[T])(implicit num: Numeric[T]): T = {
    x.groupBy(i => i).mapValues(_.length).maxBy(_._2)._1
  }

  def range[T](x: Array[T])(implicit num: Numeric[T]): T = {
    import num._
    val r = x.sorted
    r.max - r.min
  }

  def midRange[T](x: Array[T])(implicit num: Numeric[T]): Double = {
    import num._
    val r = x.sorted
    (r.max + r.min).toDouble / 2
  }
}

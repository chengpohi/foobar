package algorithm

import breeze.linalg.DenseMatrix

/**
  * scala99
  * Created by chengpohi on 9/5/16.
  */
object Search {
  def main(args: Array[String]): Unit = {
    val matrix = DenseMatrix(
      (0, 0, 0),
      (0, 1, 0),
      (0, 0, 0)
    )
    import MatrixOps.BFS
    val start = (0, 0)
    val end = (2, 2)
    matrix.routes(start, end).get.foreach(println)
  }
}



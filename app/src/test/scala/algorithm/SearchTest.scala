package algorithm

import breeze.linalg.DenseMatrix
import org.scalatest.{FlatSpec, ShouldMatchers}

/**
  * scala99
  * Created by chengpohi on 9/6/16.
  */
class SearchTest extends FlatSpec with ShouldMatchers {
  it should "list routes two nodes by bfs" in {
    val matrix = DenseMatrix(
      (0, 0, 0),
      (0, 1, 0),
      (0, 0, 0)
    )
    import MatrixOps.DFS
    val start = (0, 0)
    val end = (2, 2)
    val result: Option[List[List[(Int, Int)]]] = matrix.routes(start, end)
    assert(result === Some(List(List((0, 0), (0, 1), (0, 2), (1, 2), (2, 2)), List((0, 0), (1, 0), (2, 0), (2, 1), (2, 2)))))
  }
  it should "list routes two nodes by dfs" in {
    val matrix = DenseMatrix(
      (2, 1, 1, 0), // (0, 0), (0, 1), (0, 2)
      (1, 0, 0, 1), // (1, 0), (1, 3)
      (1, 0, 0, 1), // (2, 0), (2, 3)
      (0, 1, 1, 0)  // (3, 1), (3, 2)
    )
    import MatrixOps.BFS
    val start = (0, 0) // root node
    matrix.route
  }
}

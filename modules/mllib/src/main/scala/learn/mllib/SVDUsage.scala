package learn.mllib

import org.apache.spark.mllib.linalg.Matrix
import org.apache.spark.mllib.linalg.SingularValueDecomposition
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.linalg.distributed.RowMatrix

object SVDUsage extends MLLibApp {
  // $example on$
  val data = Array(
    Vectors.sparse(5, Seq((1, 1.0), (3, 7.0))), // other all is 0
    Vectors.dense(2.0, 0.0, 3.0, 4.0, 5.0),
    Vectors.dense(4.0, 0.0, 0.0, 6.0, 7.0))

  val rows = sc.parallelize(data)

  val mat: RowMatrix = new RowMatrix(rows)

  // Compute the top 5 singular values and corresponding singular vectors.
  val svd: SingularValueDecomposition[RowMatrix, Matrix] =
    mat.computeSVD(5, computeU = true)
  val U: RowMatrix = svd.U // The U factor is a RowMatrix.
  val s: Vector = svd.s // The singular values are stored in a local dense vector.
  val V: Matrix = svd.V // The V factor is a local dense matrix.
  // $example off$
  val collect = U.rows.collect()
  println("U factor is:")
  collect.foreach { vector => println(vector) }
  println(s"Singular values are: $s")
  println(s"V factor is:\n$V")

  sc.stop()
}

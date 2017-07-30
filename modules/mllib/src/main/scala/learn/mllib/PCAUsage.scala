package learn.mllib

import org.apache.spark.mllib.linalg.{Matrix, Vectors}
import org.apache.spark.mllib.linalg.distributed.RowMatrix

object PCAUsage extends MLLibApp {
  val data = Array(
    Vectors.sparse(5, Seq((1, 1.0), (3, 7.0))),
    Vectors.dense(2.0, 0.0, 3.0, 4.0, 5.0),
    Vectors.dense(4.0, 0.0, 0.0, 6.0, 7.0))

  val rows = sc.parallelize(data)

  val mat: RowMatrix = new RowMatrix(rows)

  // Compute the top <<4 principal components>>.
  // Principal components are stored in a local dense matrix.
  val pc: Matrix = mat.computePrincipalComponents(4)

  // Project the rows to the linear space spanned by the top 4 principal components.
  val projected: RowMatrix = mat.multiply(pc)

  val collect = projected.rows.collect()
  println("Projected Row Matrix of principal component:")
  collect.foreach { vector => println(vector) }

  sc.stop()

}

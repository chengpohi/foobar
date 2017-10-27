package learn.mllib

import org.apache.spark.mllib.feature.PCA
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD

object PCAOnSourceVectorUsage extends MLLibApp {
  //lift local collection to RDD
  val data: RDD[LabeledPoint] = sc.parallelize(
    Seq(
      new LabeledPoint(0, Vectors.dense(1, 0, 0, 0, 1)),
      new LabeledPoint(1, Vectors.dense(1, 1, 0, 1, 0)),
      new LabeledPoint(1, Vectors.dense(1, 1, 0, 0, 0)),
      new LabeledPoint(0, Vectors.dense(1, 0, 0, 0, 0)),
      new LabeledPoint(1, Vectors.dense(1, 1, 0, 0, 0))
    ))

  // Compute the top 5 principal components.
  val pca = new PCA(5).fit(data.map(_.features))

  // Project vectors to the linear space spanned by the top 5 principal
  // components, keeping the label
  val projected = data.map(p => p.copy(features = pca.transform(p.features)))

  val collect = projected.collect()

  println("Projected vector of principal component:")
  collect.foreach { vector =>
    println(vector)
  }

  sc.stop()
}

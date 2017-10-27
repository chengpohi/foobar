package learn.mllib

object ElementWiseProductUsage extends MLLibApp {
  import org.apache.spark.mllib.feature.ElementwiseProduct
  import org.apache.spark.mllib.linalg.Vectors
  val data = sc.parallelize(
    Array(Vectors.dense(1.0, 2.0, 3.0), Vectors.dense(4.0, 5.0, 6.0)))

  val transformingVector = Vectors.dense(0.0, 1.0, 2.0)
  val transformer = new ElementwiseProduct(transformingVector)

  log.warn("transform all")
  val transformedData = transformer.transform(data)
  log.warn("transform one by one")
  val transformedData2 = data.map(x => transformer.transform(x))
  transformedData2.foreach(println)
}

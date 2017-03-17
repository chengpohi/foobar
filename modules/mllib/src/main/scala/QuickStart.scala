import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by xiachen on 07/12/2016.
  */
object QuickStart {
  def main(args: Array[String]): Unit = {
    val conf =
      new SparkConf().setAppName("Simple Application").setMaster("local[4]")
    val sc = new SparkContext(conf)
    val content: RDD[String] = sc.textFile("build.sbt")
    content.foreach(println)
    println(content.count())
  }
}

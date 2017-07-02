package learn.mllib

import org.apache.spark.{SparkConf, SparkContext}

trait MLLibApp extends App {
  val conf =
    new SparkConf().setAppName("Statistical Learn").setMaster("local[2]")

  val sc: SparkContext = new SparkContext(conf)

  val SPARK_HOME = sys.env("SPARK_HOME")

  implicit class StrToHome(s: String) {
    def toMLLibPath: String = {
      SPARK_HOME + "/" + s
    }
  }

}

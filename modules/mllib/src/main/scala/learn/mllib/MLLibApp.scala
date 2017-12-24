package learn.mllib

import org.apache.log4j.Logger
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

trait MLLibApp extends App {
  lazy val log = Logger.getLogger(getClass.getName)

  val conf =
    new SparkConf().setAppName("Statistical Learn").setMaster("local[2]")

  val spark = SparkSession
    .builder()
    .config(conf)
    .getOrCreate()

  val sc: SparkContext = spark.sparkContext

  val SPARK_HOME = sys.env("SPARK_HOME")

  implicit class StrToHome(s: String) {
    def toMLLibPath: String = {
      SPARK_HOME + "/" + s
    }
  }

}

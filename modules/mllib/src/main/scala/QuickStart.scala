import learn.mllib.MLLibApp
import org.apache.spark.rdd.RDD

/**
  * Created by xiachen on 07/12/2016.
  */
object QuickStart extends MLLibApp {
  val content: RDD[String] = sc.textFile("build.sbt")
  content.foreach(println)
  println(content.count())
}

package concurrent

/**
  * scala99
  * Created by chengpohi on 10/24/15.
  */
object ScalaConcurrent {
  def main(args: Array[String]): Unit = {
    println("Start Port: 2020")
    new NetworkService(2020, 2).run()
  }
}

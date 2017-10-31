package sz

object ZipUsage extends App {

  import scalaz._
  import Scalaz._

  (IList(2) fzip IList("")).println

  val ap: IList[Int] => IList[String] = (a: IList[Int]) => a.map(_.toString)
  (IList(1) apzip ap).println

  val plus1: Endo[Int] = Endo(_ + 1)
  ((IList(1) zip IList(2)) map Zip[Endo].zip(plus1, plus1).run).println

}

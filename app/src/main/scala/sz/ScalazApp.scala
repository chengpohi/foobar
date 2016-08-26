package sz

import scalaz._
import Scalaz._

/**
  * scala99
  * Created by chengpohi on 8/26/16.
  */
trait CanTruthy[A] { self =>
  def truthys(a: A): Boolean
}

object CanTruthy {
  def truthys[A](f: A => Boolean): CanTruthy[A] = new CanTruthy[A] {
    override def truthys(a: A): Boolean = f(a)
  }
}

trait CanTruthyOps[A] {
  def self: A
  implicit def F: CanTruthy[A]
  def truthy: Boolean = F.truthys(self)
}

object ToCanIsTruthyOps {
  implicit def toCanIsTruthyOps[A](v: A)(implicit env: CanTruthy[A]) =
    new CanTruthyOps[A] {
      def self = v
      implicit def F: CanTruthy[A] = env
    }
}

object ScalazApp extends App{
  import ToCanIsTruthyOps._
  implicit val intCanTruthy: CanTruthy[Int] = CanTruthy.truthys({
    case 0 => false
    case _ => true
  })
  val r1 = 'a' to 'e'
  println(r1)
  val r2 = 'a' |-> 'e'
  println(r2)
  val r3 = 'a' |=> 'e'
  println(r3)
  println('B'.succ)
  println(implicitly[Enum[Char]].min)
  println(implicitly[Enum[Char]].max)
  val red = TrafficLight("red")
  val yellow = TrafficLight("yellow")
  val green = TrafficLight("green")
  implicit val TrafficLightEqual: Equal[TrafficLight] = Equal.equal(_ == _)
  println(red === yellow)
  println(10.truthy)
}

case class TrafficLight(name: String)

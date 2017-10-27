package sz

object LensUsage extends App {

  import scalaz._

  case class Point(x: Double, y: Double)

  case class Color(r: Byte, g: Byte, b: Byte)

  case class Turtle(position: Point, heading: Double, color: Color)

  Turtle(Point(2.0, 3.0), 0.0, Color(255.toByte, 255.toByte, 255.toByte))

  val turtlePosition = Lens.lensu[Turtle, Point]( // lens to update the point
    (a, value) => a.copy(position = value),
    _.position)

  val pointX = Lens.lensu[Point, Double]( // lens to update the x value
                                         (a, value) => a.copy(x = value),
                                         _.x)

  val turtleX = turtlePosition >=> pointX

  val t0 =
    Turtle(Point(2.0, 3.0), 0.0, Color(255.toByte, 255.toByte, 255.toByte))

  println(turtleX.get(t0))

  println(turtleX.set(t0, 5.0))

  println(turtleX.mod(_ + 1.0, t0))

  val incX = turtleX =>= {
    _ + 1.0
  }

  println(incX(t0))

  val turtleHeading = Lens.lensu[Turtle, Double](
    (a, value) => a.copy(heading = value),
    _.heading
  )

  println(turtleHeading.mod(_ + Math.sin(30), t0))
}

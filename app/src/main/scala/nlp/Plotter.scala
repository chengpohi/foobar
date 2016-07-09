package nlp

import breeze.linalg._
import breeze.plot._
/**
  * scala99
  * Created by chengpohi on 7/9/16.
  */
object Plotter {
  def main(args: Array[String]) {
    val f = Figure()
    val p = f.subplot(0)
    val x = linspace(0.0,1.0)
    p += plot(x, x :^ 2.0)
    p += plot(x, x :^ 3.0, '.')
    p.xlabel = "x axis"
    p.ylabel = "y axis"
    f.saveas("imgs/lines.png")
  }
}

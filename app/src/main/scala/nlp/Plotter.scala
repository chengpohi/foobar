package nlp

import breeze.linalg._
import breeze.plot._
import breeze.numerics._

/**
  * scala99
  * Created by chengpohi on 7/9/16.
  */
object Plotter {
  def main(args: Array[String]): Unit = {
    logPlot(2)
    logPlot(10)
  }

  def logPlot(base: Int) = {
    val f = Figure("Base: " + base)
    val p = f.subplot(0)
    val x = linspace(1.0, 10.0, 100)
    p += plot(x, log(x) / log(base))
    p.xlabel = "x axis"
    p.ylabel = "y axis"
    f.saveas("imgs/lines.png")
  }
}

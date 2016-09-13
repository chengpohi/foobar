package nlp

import breeze.linalg._
import breeze.numerics._
import breeze.plot._
import nlp.FigureDSL.PlotBuilder

/**
  * scala99
  * Created by chengpohi on 7/9/16.
  */
object FigureDSL {
  case class LinePlotDefinition[D](var x: D, var y: D)
  class PlotBuilder(implicit visible: Boolean = true) {
    val f = Figure()
    f.visible = visible
    val p = f.subplot(0)
    def display = {
      f.refresh()
      this
    }
  }

  case class HistPlotBuilder[D, V](data: D)(implicit xv: DomainFunction[D, Int, V], vv: V => Double, visible: Boolean)
    extends PlotBuilder {
    def bins(b: Int) = {
      p += breeze.plot.hist(data, b)
      this
    }

    def title(t: String) = {
      p.title = t
      this
    }
  }

  case class LinePlotBuilder[D, V](x: D)(implicit xv: DomainFunction[D, Int, V], vv: V => Double, visible: Boolean) extends PlotBuilder {
    def Y(y: D) = {
      p += plot(x, y)
      this
    }
  }

  case object hist {
    def data[D, V](d: D)(implicit xv: DomainFunction[D, Int, V], vv: V => Double, visible: Boolean) = HistPlotBuilder(d)
  }

  case object line {
    def X[D, V](x: D)(implicit xv: DomainFunction[D, Int, V], vv: V => Double, visible: Boolean) = LinePlotBuilder(x)
  }
}

object FigurePlotter {
  def apply(plotBuilder: PlotBuilder) = {
    plotBuilder.display
  }
}

object Plotter {
  def main(args: Array[String]): Unit = {
    import FigureDSL._
    implicit val visible: Boolean = true
    FigurePlotter {
      val g = breeze.stats.distributions.Gaussian(0, 1).sample(100000)
      hist data g bins 100 title "Hello World"
    }

    FigurePlotter {
      val x = linspace(0.1, 10.0, 100)
      line X x Y log(x)
    }
  }
}

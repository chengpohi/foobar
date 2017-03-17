package algorithm

import breeze.linalg.DenseMatrix

import scalaz.Scalaz._

/**
  * scala99
  * Created by chengpohi on 9/5/16.
  */
object MatrixOps {
  type XY = (Int, Int)
  implicit class DFS(denseMatrix: DenseMatrix[Int]) {
    def routes(start: XY, end: XY): Option[List[List[XY]]] = {
      val (startX, startY) = start
      val (endX, endY) = end

      if (startX > endX || startY > endY)
        return None
      if (startX == endX && startY == endY)
        return List(List(end)).some

      val down = startX + 1 >= denseMatrix.rows match {
        case true => None
        case false => next((startX + 1, startY), end)
      }
      val right = startY + 1 >= denseMatrix.cols match {
        case true => None
        case false => next((startX, startY + 1), end)
      }
      if (down == None && right == None)
        return None
      if (down == None && right != None)
        return appendStart(start, right)
      if (down != None && right == None)
        return appendStart(start, down)
      if (down != None && right != None)
        return appendStart(start, (right.get ++ down.get).some)
      None
    }
    def appendStart(start: (Int, Int), right: Option[List[List[(Int, Int)]]])
      : Option[List[List[(Int, Int)]]] = {
      right.map(i => i.map(j => start +: j))
    }
    def next(start: (Int, Int), end: XY): Option[List[List[XY]]] = {
      val x = start._1
      val y = start._2
      denseMatrix.apply(x, y) match {
        case 1 => None
        case 0 =>
          routes((x, y), end) match {
            case None => None
            case Some(res) => res.some
          }
      }
    }
  }
  implicit class BFS(denseMatrix: DenseMatrix[Int]) {
    def route(x: Int): List[Int] = {
      val xs: List[Int] = next(x)
      xs ::: rec(xs)
    }

    def rec(xs: List[Int]): List[Int] = {
      val ls: List[Int] = xs.flatMap(i => next(i))
      ls.empty match {
        case true => List()
        case false => ls ::: rec(ls)
      }
    }

    def next(x: Int) =
      denseMatrix(x, ::).inner.toArray.toList.zipWithIndex
        .filter(a => a._1 != 0 & a._2 > x)
        .map(_._2)
  }
}

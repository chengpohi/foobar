package algorithm

/**
  * scala99
  * Created by chengpohi on 11/1/15.
  */
object TruthTable {
  def and(a: Boolean, b: Boolean): Boolean = a && b

  def or(a: Boolean, b: Boolean): Boolean = a || b

  def not(a: Boolean): Boolean = !a

  def equ(a: Boolean, b: Boolean): Boolean = or(and(a, b), and(not(a), not(b)))

  def table2(
      f: (Boolean, Boolean) => Boolean): List[(Boolean, Boolean, Boolean)] = {
    val two = List(true, false)
    for (a <- two; b <- two) yield (a, b, f(a, b))
  }

  def table3(f: (Boolean, Boolean, Boolean) => Boolean)
    : List[(Boolean, Boolean, Boolean, Boolean)] = {
    val two = List(true, false)
    for (a <- two; b <- two; c <- two) yield (a, b, c, f(a, b, c))
  }
}

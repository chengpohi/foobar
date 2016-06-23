package dsl

import scala.collection.mutable

/**
  * baysik for scala dsl
  * see <a href="https://github.com/fogus/baysick">Baysik</a>
  * Created by chengpohi on 6/22/16.
  */
class Baysick {

  abstract sealed class BasicLine

  case class PrintString(num: Int, s: String) extends BasicLine

  case class PrintResult(num: Int, fn: () => String) extends BasicLine

  case class PrintVariable(num: Int, s: Symbol) extends BasicLine

  case class PrintNumber(num: Int, number: BigInt) extends BasicLine

  case class Goto(num: Int, to: Int) extends BasicLine

  case class Input(num: Int, name: Symbol) extends BasicLine

  case class Let(num: Int, fn: () => Unit) extends BasicLine

  case class If(num: Int, fn: () => Boolean, thenJmp: Int) extends BasicLine

  case class End(num: Int) extends BasicLine

  class Bindings[T, U] {
    val atoms = mutable.HashMap[Symbol, T]()
    val numerics = mutable.HashMap[Symbol, U]()

    def set[X >: T with U](k: Symbol, v: X) = v match {
      case u: U => numerics(k) = u
      case t: T => atoms(k) = t
    }

    def atom(s: Symbol): T = atoms(s)

    def num(s: Symbol): U = numerics(s)

    def any(s: Symbol): Any = (atoms.get(s), numerics.get(s)) match {
      case (Some(x), None) => x
      case (None, Some(x)) => x
      case (None, None) => None
      case (Some(x), Some(y)) => Some(x, y)
    }
  }

  val lines = new mutable.HashMap[Int, BasicLine]()
  val binds = new Bindings[String, Int]
  val returnStack = new mutable.Stack[Int]

  case class Assignment(s: Symbol) {
    def :=[A](v: A): () => Unit = () => v match {
      case v: String => binds.set(s, v)
      case v: Int => binds.set(s, v)
    }

    def :=(v: () => Int): () => Unit = () => binds.set(s, v())
  }

  case class MathFunction(lhs: () => Int) {
    def *(rhs: Int): () => Int = () => lhs() * rhs

    def *(rhs: () => Int): () => Int = () => lhs() * rhs()

    def /(rhs: Int): () => Int = () => lhs() / rhs

    def /(rhs: () => Int): () => Int = () => lhs() / rhs()

    def +(rhs: Symbol): () => Int = () => lhs() + binds.num(rhs)

    def +(rhs: () => Int): () => Int = () => lhs() + rhs()

    def -(rhs: Symbol): () => Int = () => lhs() - binds.num(rhs)

    def -(rhs: () => Int): () => Int = () => lhs() - rhs()
  }

  case class BinaryRelation(lhs: () => Int) {
    def ===(rhs: Int): () => Boolean = () => lhs() == rhs

    def <=(rhs: Int): () => Boolean = () => lhs() <= rhs

    def <=(rhs: Symbol): () => Boolean = () => lhs() <= binds.num(rhs)

    def >=(rhs: Int): () => Boolean = () => lhs() >= rhs

    def >=(rhs: Symbol): () => Boolean = () => lhs() >= binds.num(rhs)

    def <(rhs: Int): () => Boolean = () => lhs() < rhs

    def >(rhs: Int): () => Boolean = () => lhs() > rhs
  }

  case class Branch(num: Int, fn: () => Boolean) {
    def THEN(loc: Int) = lines(num) = If(num, fn, loc)
  }

  def stringify(x: Any*): String = x.mkString("")

  case class Appendr(lhs: Any) {
    var appendage = lhs match {
      case sym: Symbol => () => binds.any(sym).toString
      case fn: Function0[Any] => fn
      case _ => () => lhs.toString
    }

    def %(rhs: Any): () => String = {
      () => rhs match {
        case sym: Symbol => stringify(appendage(), binds.any(sym))
        case fn: Function0[Any] => stringify(appendage(), fn())
        case _ => stringify(appendage(), rhs)
      }
    }
  }

  def SQRT(i: Int): () => Int = () => Math.sqrt(i.intValue).intValue

  def SQRT(s: Symbol): () => Int = () => Math.sqrt(binds.num(s)).intValue

  def ABS(i: Int): () => Int = () => Math.abs(i)

  def ABS(s: Symbol): () => Int = () => Math.abs(binds.num(s))

  def RUN() = gotoLine(lines.keys.toList.sorted.head)

  case class LineBuilder(num: Int) {
    def END() = lines(num) = End(num)

    object PRINT {
      def apply(str: String) = lines(num) = PrintString(num, str)

      def apply(number: BigInt) = lines(num) = PrintNumber(num, number)

      def apply(s: Symbol) = lines(num) = PrintVariable(num, s)

      def apply(fn: () => String) = lines(num) = PrintResult(num, fn)
    }

    object INPUT {
      def apply(name: Symbol) = lines(num) = Input(num, name)
    }

    object LET {
      def apply(fn: () => Unit) = lines(num) = Let(num, fn)
      def update(s: Symbol, v: Any) = lines(num) = Let(num, Assignment(s) := v)
    }

    object GOTO {
      def apply(to: Int) = lines(num) = Goto(num, to)
    }

    object IF {
      def apply(fn: () => Boolean) = Branch(num, fn)
    }

  }

  private def gotoLine(line: Int) {
    lines(line) match {
      case PrintNumber(_, number: BigInt) => {
        println(number)
        gotoLine(line + 10)
      }
      case PrintString(_, s: String) => {
        println(s)
        gotoLine(line + 10)
      }
      case PrintResult(_, fn: Function0[String]) => {
        println(fn())
        gotoLine(line + 10)
      }
      case PrintVariable(_, s: Symbol) => {
        println(binds.any(s))
        gotoLine(line + 10)
      }
      case Input(_, name) => {
        val entry = readLine

        // Temporary hack
        try {
          binds.set(name, java.lang.Integer.parseInt(entry))
        }
        catch {
          case _: Throwable => binds.set(name, entry)
        }

        gotoLine(line + 10)
      }
      case Let(_, fn: Function0[Unit]) => {
        fn()
        gotoLine(line + 10)
      }
      case If(_, fn: Function0[Boolean], thenJmp: Int) => {
        if (fn()) {
          gotoLine(thenJmp)
        }
        else {
          gotoLine(line + 10)
        }
      }
      case Goto(_, to) => gotoLine(to)
      case End(_) => {
        println()
        println("BREAK IN LINE " + line)
      }
    }

  }

  implicit def int2LineBuilder(i: Int) = LineBuilder(i)

  implicit def toAppendr(key: Any) = Appendr(key)

  implicit def symbol2Assignment(sym: Symbol) = Assignment(sym)

  implicit def symbol2BinaryRelation(sym: Symbol) = BinaryRelation(() => binds.num(sym))

  implicit def fnOfInt2BinaryRelation(fn: () => Int) = BinaryRelation(fn)

  implicit def symbol2MathFunction(sym: Symbol) = MathFunction(() => binds.num(sym))

  implicit def fnOfInt2MathFunction(fn: () => Int) = MathFunction(fn)

}

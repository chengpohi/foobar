package implicits

import scala.collection.mutable.ListBuffer

/**
  * Created by xiachen on 15/12/2016.
  */
object TransactionDemo {
  def transaction[T](op: Transaction => T) = {
    val trans: Transaction = new Transaction
    op(trans)
    trans.commit()
  }

  def f1(x: Int)(implicit thisTransaction: Transaction): Int = {
    thisTransaction.println(s"first step: $x")
    f2(x + 1)
  }

  def f2(x: Int)(implicit thisTransaction: Transaction): Int = {
    thisTransaction.println(s"second step: $x")
    f3(x * x)
  }

  def f3(x: Int)(implicit thisTransaction: Transaction): Int = {
    thisTransaction.println(s"third step: $x")
    if (x % 2 != 0) thisTransaction.abort()
    x
  }

  def main(args: Array[String]): Unit = {
    transaction {
      implicit thisTransaction => {
        val res1 = f1(1)
        println(res1)
        println(if (thisTransaction.isAborted) "aborted" else s"result: $res1")
      }
    }
  }
}

class Transaction {
  private val log = new ListBuffer[String]

  def println(s: String): Unit = log += s

  private var aborted = false
  private var committed = false

  def abort(): Unit = {
    aborted = true
  }

  def isAborted = aborted

  def commit(): Unit =
    if (!aborted && !committed) {
      Console.println("******* log ********")
      log.foreach(Console.println)
      committed = true
    }
}

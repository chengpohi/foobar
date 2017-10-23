package sz

import scalaz.Memo

/**
  * scala99
  * Created by chengpohi on 8/8/16.
  */
object Memomization {
  val slowFib: Int => Int = {
    case 0 => 0
    case 1 => 1
    case n => slowFib(n - 1) + slowFib(n - 2)
  }
  //not thread safe, since it's using mutable.HashMap
  val memoizedFib: Int => Int = Memo.mutableHashMapMemo {
    case 0 => 0
    case 1 => 1
    case n => memoizedFib(n - 2) + memoizedFib(n - 1)
  }
  val memoizedA = Memo.arrayMemo(20)
  def main(args: Array[String]): Unit = {
    slowFib(20)
    slowFib(30)
    println(System.currentTimeMillis())
    slowFib(40)
    println(System.currentTimeMillis())
    memoizedFib(20)
    memoizedFib(30)
    println(System.currentTimeMillis())
    memoizedFib(40)
    println(System.currentTimeMillis())
  }

}

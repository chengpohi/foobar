package fpinscala

import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.{Callable, CountDownLatch, TimeUnit}

import scalaz.Scalaz._
import scalaz._

object FPinScala extends App {
  def isSorted[A](arr: Array[A], f: (A, A) => Boolean): Boolean = {
    arr.sliding(2).map(i => f(i.head, i.last)).forall(b => b)
  }

  val res = isSorted(Array(1, 2, 3, 4), (a: Int, b: Int) => a < b)
  assert(res)
  val res1 = isSorted(Array(6, 2, 3, 4), (a: Int, b: Int) => a < b)
  assert(!res1)

  val res2 = isSorted(Array(6, 5, 4, 3), (a: Int, b: Int) => a > b)
  val fib: Int => Int = {
    case 0 => 0
    case 1 => 1
    case n => fib(n - 1) + fib(n - 2)
  }

  assert(fib(1) == 1)
  assert(fib(2) == 1)
  assert(fib(3) == 2)

  def curry[A, B, C](f: (A, B) => C): A => (B => C) =
    (a: A) => (b: B) => f(a, b)
  def uncurry[A, B, C](f: A => B => C): (A, B) => C = { (a: A, b: B) =>
    f(a)(b)
  }

  def compose[A, B, C](f: B => C, g: A => B): A => C = (a: A) => f(g(a))

  def tail[A](l: List[A]): List[A] = {
    l match {
      case h :: t => t
      case Nil    => throw new UnsupportedOperationException("tail of empty list")
    }
  }

  def setHead[A](l: List[A], a: A): List[A] = a :: tail(l)

  def drop[A](l: List[A], n: Int): List[A] = {
    (1 to n).foldLeft(l)((i, k) => tail(i))
  }

  def dropWhile[A](l: List[A])(f: A => Boolean): List[A] = {
    l match {
      case h :: t if f(h) => dropWhile(t)(f)
      case _              => l
    }
  }

  tail(List(1, 2, 3)).println
  setHead(List(1, 2, 3), 5).println

  drop(List(1, 2, 3), 2).println
  dropWhile(List(1, 2, 3))(i => i < 2).println

  /*  trait Par[A] {
      def unit(a: A): Par[A]
      def map2[B,C](a: Par[A], b: Par[B])(f: (A,B) => C): Par[C]
      def fork(a: => Par[A]): Par[A]
      def lazyUnit(a: => A): Par[A] = fork(unit(a))
      def run(a: Par[A]): A
    }*/
  class ExecutorService {
    def submit[A](a: Callable[A]): Future[A] = ???
  }

  trait Future[A] {
    def get: A
    def get(timeout: Long, unit: TimeUnit): A
    def cancel(evenIfRunning: Boolean): Boolean
    def isDone: Boolean
    def isCancelled: Boolean
    def apply(k: A => Unit): Unit
  }

  type Par[A] = ExecutorService => Future[A]

  object Par {
    def unit[A](a: A): Par[A] = (es: ExecutorService) => UnitFuture(a)

    private case class UnitFuture[A](get: A) extends Future[A] {
      def isDone = true
      def get(timeout: Long, units: TimeUnit) = get
      def isCancelled = false
      def cancel(evenIfRunning: Boolean): Boolean = false
      def apply(k: A => Unit): Unit = ???
    }

    def map2[A, B, C](a: Par[A], b: Par[B])(f: (A, B) => C)(
        implicit timeout: Long = Long.MaxValue,
        unit: TimeUnit = TimeUnit.SECONDS): Par[C] = (es: ExecutorService) => {
      val af = a(es)
      val bf = b(es)
      UnitFuture(f(af.get(timeout, unit), bf.get(timeout, unit)))
    }

    def map[A, B](a: Par[A])(f: A => B): Par[B] =
      map2(a, unit(()))((a, _) => f(a))

    def asyncF[A, B](f: A => B): A => Par[B] = {
      (a: A) => (es: ExecutorService) =>
        {
          UnitFuture(f(a))
        }
    }

    def parMap[A, B](ps: List[A])(f: A => B): Par[List[B]] = {
      val res = ps.map(asyncF(f))
      sequence(res)
    }
    def parFilter[A, B](ps: List[A])(f: A => Boolean): Par[List[A]] = {
      val res = ps
        .map(unit)
        .map(a =>
          map2(a, map(a)(f))((a: A, b: Boolean) => if (b) Some(a) else None))
      map(sequence(res))(l => l.flatten)
    }

    def sequence[A](l: List[Par[A]]): Par[List[A]] = l match {
      case List()  => unit(List())
      case List(i) => map(i)(f => List(f))
      case _ =>
        val (a, b) = l.splitAt(l.length / 2)
        map2(sequence(a), sequence(b))(_ ++ _)
    }

    def sortPar(parList: Par[List[Int]]) = map(parList)(_.sorted)

    def fork[A](a: => Par[A]): Par[A] =
      (es: ExecutorService) =>
        new Future[A] {
          override def get: A = ???
          override def get(timeout: Long, unit: TimeUnit): A = ???
          override def cancel(evenIfRunning: Boolean): Boolean = ???
          override def isDone: Boolean = ???
          override def isCancelled: Boolean = ???
          override def apply(k: A => Unit): Unit = {
            eval(es)(a(es)(k))
          }
      }

    def eval(es: ExecutorService)(r: => Unit): Unit = {
      es.submit(new Callable[Unit] {
        def call = r
      })
    }

    def delay[A](fa: => Par[A]): Par[A] = es => fa(es)
    def run[A](es: ExecutorService)(p: Par[A]): A = {
      val ref = new AtomicReference[A]()
      val latch = new CountDownLatch(1)
      p(es) { i =>
        ref.set(i); latch.countDown()
      }
      latch.await()
      ref.get()
    }
  }

}

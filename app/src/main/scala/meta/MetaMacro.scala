package meta

/**
  * Created by xiachen on 14/11/2016.
  */
@main
object MetaMacro {
  println("hello")

  println(foo())
  println(TestMethods.bar("Hello", "World"))
  @StrPrefix
  def foo(): String = {
    "Hello World"
  }

  @AutoVarargs
  def add(x: Int, y: Int): Int = x + y

  println(add(2, 3, 6, 7, 8))

}

object TestMethods {
  @AP("Foo Bar: %s, %s")
  def bar(s1: String, s2: String): String
}

object VarrApp extends App {
  @Varr
  val names = Vector("a", "b", "c")

  println(a)
  println(b)
  println(c)
}

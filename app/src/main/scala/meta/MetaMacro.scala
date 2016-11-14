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

}

object TestMethods {
  @AP("Foo Bar: %s, %s")
  def bar(s1: String, s2: String): String
}

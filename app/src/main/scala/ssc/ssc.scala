package ssc

object ssc extends App {
  val pkg = implicitly[sourcecode.Pkg]
  println(pkg.value)

  val fullName = implicitly[sourcecode.FullName]
  println(fullName.value)

  val file = implicitly[sourcecode.File]

  println(file.value)

  val line = implicitly[sourcecode.Line]

  println(line.value)

  //implicitly will automatically try to find implicits in companion object
  val i: TestImplicitly = implicitly[TestImplicitly]
  println(i.value)
}

case class TestImplicitly(value: String)
object TestImplicitly {
  implicit def generate: TestImplicitly = TestImplicitly("foo")
}

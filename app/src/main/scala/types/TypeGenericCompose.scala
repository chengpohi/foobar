package types

object TypeGenericCompose extends App {
  case class AA[A, B](a: A, b: B)

  type TT = Int AA Boolean

  val a: TT = AA(1, true)
  println(a)
}

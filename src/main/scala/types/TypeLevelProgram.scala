package types

import shapeless.test.illTyped

/**
  * scala99
  * Created by chengpohi on 6/10/16.
  */
object TypeLevelProgram {
  def main(args: Array[String]): Unit = {
    implicitly[TrueType =:= TrueType]
    implicitly[FalseType =:= FalseType]
    implicitly[TrueType#Not =:= FalseType]
    implicitly[FalseType#Not =:= TrueType]
    implicitly[TrueType#Or[TrueType] =:= TrueType]
    implicitly[TrueType#Or[FalseType] =:= TrueType]
    implicitly[FalseType#Or[FalseType] =:= FalseType]
    implicitly[FalseType#Or[TrueType] =:= TrueType]

    illTyped("implicitly[TrueType =:= FalseType]")
    illTyped("implicitly[FalseType =:= TrueType]")
  }
}

sealed trait BoolType {
  type Not <: BoolType
  type Or[That <: BoolType] <: BoolType
}

sealed trait TrueType extends BoolType {
  override type Not = FalseType
  override type Or[That <: BoolType] = TrueType
}

sealed trait FalseType extends BoolType {
  override type Not = TrueType
  override type Or[That <: BoolType] = That
}

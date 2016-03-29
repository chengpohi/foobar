/**
  * scala99
  * Created by chengpohi on 3/28/16.
  */
object MacroApp {
  def main(args: Array[String]): Unit = {
    val dynamicMacros: DynamicMacros = new DynamicMacros(Map("name" -> "chengpohi"))
    println(dynamicMacros.name)
    val u = User("chengpohi", Some(123))
    val u2: User = WithIdExample.withId(u, Some(456))
    println(u2)
    import CaseClassToMap.Mappable
    println(Tt("chengpohi", 456).toMap)
  }

  case class User(name: String, id: Option[Int])
  case class Tt(name: String, id: Int) extends Model
}

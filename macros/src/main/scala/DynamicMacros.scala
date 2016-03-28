/**
  * scala99
  * Created by chengpohi on 3/28/16.
  */
import scala.language.dynamics

class DynamicMacros(fields: Map[String, Any]) extends Dynamic{
  def selectDynamic(fieldName: String) = fields(fieldName)
}

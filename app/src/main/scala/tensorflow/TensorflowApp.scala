package tensorflow

import org.tensorflow.{Graph, Tensor, TensorFlow}
import org.tensorflow.Session

object TensorflowApp extends App {
  val graph = new Graph()

  val value = "Hello from " + TensorFlow.version

  val t = Tensor.create(value.getBytes("UTF-8"))

  graph.opBuilder("Const", "MyConst").setAttr("dtype", t.dataType).setAttr("value", t).build


  val s = new Session(graph)
  val output = s.runner.fetch("MyConst").run.get(0)
  println("output:")
  println(new String(output.bytesValue, "UTF-8"))

  output.close()
  s.close()
}

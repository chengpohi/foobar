package tensorflow

import java.nio.file.{Files, Paths}
import java.util.stream.Collectors

import akka.util.BoxedType
import org.tensorflow._
import org.tensorflow.types.UInt8


object LabelImage extends App {
  val modelDir = "./inception5h"
  val imageFile = "./inception5h/download.jpeg"
  val graphDef: Array[Byte] = Files.readAllBytes(Paths.get(modelDir, "tensorflow_inception_graph.pb"))
  val labels =
    Files.lines(Paths.get(modelDir, "imagenet_comp_graph_label_strings.txt")).collect(Collectors.toList())
  val imageBytes = Files.readAllBytes(Paths.get(imageFile))

  val image = constructAndExecuteGraphToNormalizeImage(imageBytes)

  val labelProbabilities = executeInceptionGraph(graphDef, image)
  val bestLabelIdx = labelProbabilities.zipWithIndex.maxBy(_._1)._2

  printf("BEST MATCH: %s (%.2f%% likely)", labels.get(bestLabelIdx), labelProbabilities(bestLabelIdx) * 100f)

  def executeInceptionGraph(graphDef: Array[Byte], image: Tensor[Float]): Array[Float] = try {
    val g = new Graph()
    try {
      g.importGraphDef(graphDef)
      try {
        val s = new Session(g)
        val result = s.runner.feed("input", image).fetch("output").run.get(0).expect(BoxedType.apply(classOf[Float]))
        try {
          val rshape = result.shape
          if ((result.numDimensions != 2) || rshape(0) != 1)
            throw new RuntimeException(String.format("Expected model to produce a [1 N] shaped tensor where N is the number of labels, instead it produced one with shape %s", java.util.Arrays.toString(rshape)))

          val nlabels = rshape(1).toInt
          result.copyTo(Array.ofDim[Float](1, nlabels))(0)
        } finally {
          if (s != null) s.close()
          if (result != null) result.close()
        }
      }
    } finally if (g != null) g.close()
  }

  def constructAndExecuteGraphToNormalizeImage(bytes: Array[Byte]): Tensor[Float] = {
    val graph = new Graph()
    val builder = new GraphBuilder(graph)
    val H: Int = 224
    val W: Int = 224
    val mean = 117f
    val scale = 1f
    val input = builder.constant("input", imageBytes)


    val make_batch: Output[Int] = builder.constant("make_batch", 0)
    val images: Output[Float] = builder.expandDims(
      builder.cast(builder.decodeJpeg(input, 3), BoxedType.apply(classOf[Float])),
      make_batch)

    val resize: Output[Float] = builder.resizeBilinear(
      images,
      builder.constant("size", Array(H, W))
    )
    val sub: Output[Float] = builder.sub(
      resize,
      builder.constant("mean", mean)
    )
    val output = builder.div(
      sub,
      builder.constant("scale", scale)
    )
    val session = new Session(graph)

    session.runner().fetch(output.op().name()).run().get(0).expect(classOf[java.lang.Float]).asInstanceOf[Tensor[Float]]
  }

  class GraphBuilder(g: Graph) {
    def div(x: Output[Float], y: Output[Float]): Output[Float] = binaryOp("Div", x, y)
    def sub[T](x: Output[T], y: Output[T]): Output[T] = binaryOp("Sub", x, y)
    def resizeBilinear[T](images: Output[T], size: Output[Int]): Output[T] = binaryOp3("ResizeBilinear", images, size)
    def expandDims[T](input: Output[T], dim: Output[Int]): Output[T] =
      binaryOp3("ExpandDims", input, dim)
    def cast[T, U](value: Output[T], `type`: Class[_]): Output[U] = {
      val dtype = DataType.fromClass(`type`)
      g.opBuilder("Cast", "Cast").addInput(value).setAttr("DstT", dtype).build.output[U](0)
    }
    def decodeJpeg(contents: Output[String], channels: Long): Output[UInt8] = g.opBuilder("DecodeJpeg", "DecodeJpeg").addInput(contents).setAttr("channels", channels).build.output[UInt8](0)
    def constant[T](name: String, value: Any, `type`: Class[_]): Output[T] = try {
      val t = Tensor.create(value, `type`)
      try
        g.opBuilder("Const", name).setAttr("dtype", DataType.fromClass(`type`)).setAttr("value", t).build.output[T](0)
      finally if (t != null) t.close()
    }
    def constant(name: String, value: Array[Byte]): Output[String] = this.constant(name, value, classOf[String])
    def constant(name: String, value: Int): Output[Int] = this.constant(name, value, BoxedType.apply(classOf[Int]))
    def constant(name: String, value: Array[Int]): Output[Int] = this.constant(name, value, BoxedType.apply(classOf[Int]))
    def constant(name: String, value: Float): Output[Float] = this.constant(name, value, BoxedType.apply(classOf[Float]))
    def binaryOp[T](`type`: String, in1: Output[T], in2: Output[T]): Output[T] = g.opBuilder(`type`, `type`).addInput(in1).addInput(in2).build.output[T](0)
    def binaryOp3[T, U, V](`type`: String, in1: Output[U], in2: Output[V]) = g.opBuilder(`type`, `type`).addInput(in1).addInput(in2).build.output[T](0)
  }

}

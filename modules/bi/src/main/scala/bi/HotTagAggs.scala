package bi

import java.io.{DataInput, DataOutput, IOException}

import org.apache.commons.logging.LogFactory
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io._
import org.apache.hadoop.mapred._

import scala.util.Try

object HotTagAggs {
  val log = LogFactory.getLog(this.getClass)

  class TagHotWritable(var tag: String, var hot: Int) extends WritableComparable[TagHotWritable] {
    def this() {
      this("", 0)
    }
    override def readFields(in: DataInput): Unit = {
      tag = in.readUTF()
      hot = in.readInt()
    }
    override def write(out: DataOutput): Unit = {
      out.writeUTF(tag)
      out.writeInt(hot)
    }

    override def toString: String =
      s"""
        |{
        |"tag": $tag,
        |"hot": $hot
        |}
      """.stripMargin
    override def compareTo(o: TagHotWritable): Int =
      Integer.compare(o.hot, hot)
  }

  class Map extends MapReduceBase with Mapper[LongWritable, Text, Text, TagHotWritable] {
    private val text = new Text()
    private val UPPER_PATTERN = "^[A-Z].*".r
    private val LOWER_PATTERN = "^[a-z].*".r
    private val NUMBER_PATTERN = "^[0-9].*".r

    @throws[IOException]
    def map(key: LongWritable, value: Text,
            output: OutputCollector[Text, TagHotWritable],
            reporter: Reporter) {
      val line: String = value.toString
      val strings = line.trim.split("\\t")
      strings match {
        case Array(t, count) => {
          val tag = t.trim
          val hot = Try(count.trim.toInt).getOrElse(0)
          val writable = new TagHotWritable(tag, hot)
          tag match {
            case UPPER_PATTERN() =>
              text.set("upper_tag")
              output.collect(text, writable)
            case LOWER_PATTERN() =>
              text.set("lower_tag")
              output.collect(text, writable)
            case NUMBER_PATTERN() =>
              text.set("number_tag")
              output.collect(text, writable)
            case _ =>
              println("invalid tag: " + tag)
          }
        }
        case _ =>
          println("invalid data" + strings)
      }
    }
  }

  class Reduce extends MapReduceBase with
    Reducer[Text, TagHotWritable, Text, TagHotWritable] {
    @throws[IOException]
    def reduce(key: Text,
               values: java.util.Iterator[TagHotWritable],
               output: OutputCollector[Text, TagHotWritable],
               reporter: Reporter) {
      import scala.collection.JavaConverters._
      var hot = 0
      var tag = ""
      for (v <- values.asScala) {
        if (v.hot >= hot) {
          hot = v.hot
          tag = v.tag
        }
      }
      output.collect(key, new TagHotWritable(tag, hot))
    }
  }

  @throws[Exception]
  def main(args: Array[String]) {
    val conf: JobConf = new JobConf(this.getClass)
    conf.setJobName("HotTagAggs")
    conf.setOutputKeyClass(classOf[Text])
    conf.setOutputValueClass(classOf[TagHotWritable])
    conf.setMapperClass(classOf[Map])

    conf.setCombinerClass(classOf[Reduce])
    conf.setReducerClass(classOf[Reduce])
    conf.setInputFormat(classOf[TextInputFormat])
    conf.setOutputFormat(classOf[TextOutputFormat[Text, TagHotWritable]])

    FileInputFormat.setInputPaths(conf, new Path(args(0)))
    FileOutputFormat.setOutputPath(conf, new Path(args(1)))
    JobClient.runJob(conf)
  }
}
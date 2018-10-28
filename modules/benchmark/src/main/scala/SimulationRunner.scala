import java.net.URLClassLoader

import com.github.chengpohi.simulations.NginxSimulation

import scala.sys.process.Process

object SimulationRunner {
  val JAVA_OPTS = """-Xms4g -Xmx4g -XX:+UseG1GC -XX:MaxGCPauseMillis=30 -XX:G1HeapRegionSize=16m -XX:InitiatingHeapOccupancyPercent=75 -XX:+ParallelRefProcEnabled -XX:+PerfDisableSharedMem -XX:+AggressiveOpts -XX:+OptimizeStringConcat -Djava.net.preferIPv4Stack=true -Djava.net.preferIPv6Addresses=false"""

  def main(args: Array[String]) {
    val sim = classOf[NginxSimulation].getName

    val urls = Thread.currentThread.getContextClassLoader.asInstanceOf[URLClassLoader].getURLs
    val command = Seq(System.getProperty("java.home") + "/bin/java") ++
      JAVA_OPTS.split("\\s+") ++
      Seq("-Dfile.encoding=UTF-8", "-classpath", urls.map(_.getPath).mkString(":")) ++
      Seq("io.gatling.app.Gatling") ++
      Seq("-s", sim)

    val p = Process(command)
    p.!
  }
}

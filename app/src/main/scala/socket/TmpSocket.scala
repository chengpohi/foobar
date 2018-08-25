package socket

import java.io.{BufferedReader, InputStreamReader, PrintWriter}
import java.net.{InetSocketAddress, ServerSocket, Socket}

object TmpSocket extends App {
  val socket = new ServerSocket()
  socket.bind(new InetSocketAddress("127.0.0.1", 8888))
  while (true) {
    val sc: Socket = socket.accept()
    val reader = new BufferedReader(new InputStreamReader(sc.getInputStream))
    val writer = new PrintWriter(sc.getOutputStream, true)
    Iterator
      .continually(reader.readLine())
      .takeWhile(_ != null)
      .foreach(line => {
        println(line)
        writer.println("received")
      })
    sc.close()
  }
}

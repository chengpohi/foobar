package concurrent

import java.net.ServerSocket
import java.util.concurrent.{ExecutorService, Executors}

/**
  * Default (Template) Project
  * Created by chengpohi on 7/2/16.
  */
class NetworkService(port: Int, poolSize: Int) extends Runnable {
  val serverSocket = new ServerSocket(port)
  val pool: ExecutorService = Executors.newFixedThreadPool(poolSize)

  def run() {
    try {
      while (true) {
        // This will block until a connection comes in.
        val socket = serverSocket.accept()
        pool.execute(new Handler(socket))
      }
    } finally {
      pool.shutdown()
    }
  }
}

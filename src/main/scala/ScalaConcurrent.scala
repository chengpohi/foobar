import java.net.{ServerSocket, Socket}
import java.util.concurrent.{ExecutorService, Executors}

/**
 * scala99
 * Created by chengpohi on 10/24/15.
 */
object ScalaConcurrent{
  def main(args: Array[String]): Unit = {
    println("Start Port: 2020")
    new NetworkService(2020, 2).run
  }
}

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

class Handler(socket: Socket) extends Runnable {
  def message = (Thread.currentThread.getName() + "\n").getBytes

  def run() {
    socket.getOutputStream.write(message)
    socket.getOutputStream.close()
  }
}
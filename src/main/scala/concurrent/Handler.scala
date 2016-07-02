package concurrent

import java.net.Socket

/**
  * Default (Template) Project
  * Created by chengpohi on 7/2/16.
  */
class Handler(socket: Socket) extends Runnable {
  def message = (Thread.currentThread.getName() + "\n").getBytes

  def run() {
    socket.getOutputStream.write(message)
    socket.getOutputStream.close()
  }
}

package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Objects;

public class EchoServer {
    public static final String GREETING = "Hello I must be going.\r\n";

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress("127.0.0.1", 8888));
        ByteBuffer buffer = ByteBuffer.wrap(GREETING.getBytes());
        ssc.configureBlocking(false);

        while (true) {
            SocketChannel sc = ssc.accept();

            if (Objects.isNull(sc)) {
                System.out.println("sleeping");
                Thread.sleep(2000);
            } else {
                System.out.println("Incoming connect from:" + sc.getRemoteAddress());
                buffer.rewind();
                sc.write(buffer);
                sc.close();
            }
        }
    }

}

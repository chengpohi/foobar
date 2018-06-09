package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class EchoClient {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        SocketChannel sc = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));
        sc.configureBlocking(false);
        sc.register(selector, SelectionKey.OP_ACCEPT);
        while (!sc.finishConnect()) {
            System.out.println("not finished");
        }
        ByteBuffer allocate = ByteBuffer.allocate(2000);
        sc.read(allocate);
        System.out.println(new String(allocate.array()));
    }
}

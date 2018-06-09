package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
    public static void main(String[] args) throws Exception {

        String host = "localhost";
        int port = 8080;
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            EchoClientHandler clientHandler = new EchoClientHandler();
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(
                    new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            //                    ch.pipeline().addLast(
                            //                            new RequestDataEncoder(),
                            //                            new ResponseDataDecoder(),
                            //                            new ClientHandler());
                            ch.pipeline().addLast(clientHandler);
                        }
                    });

            ChannelFuture f = b.connect(host, port).sync();

            RequestData msg = new RequestData();
            msg.setIntValue(110);
            msg.setStringValue("all work and no play makes jack a dull boy");
            Channel channel = f.awaitUninterruptibly().channel();
//            channel.writeAndFlush(msg).sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}

package netty.http

import io.netty.bootstrap.ServerBootstrap
import io.netty.buffer.Unpooled
import io.netty.channel.{ChannelFutureListener, _}
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.codec.http._
import io.netty.handler.ssl.{SslContext, SslHandler}
import io.netty.util.CharsetUtil
import netty.HttpSnoopServer

class HttpPipelineInitializer(client: Boolean) extends ChannelInitializer[Channel] {
  override def initChannel(ch: Channel): Unit = {
    val pipeline = ch.pipeline()

    if (client) {
      pipeline.addLast("decoder", new HttpResponseDecoder())
      pipeline.addLast("encoder", new HttpResponseEncoder())
    } else {
      pipeline.addLast("decoder", new HttpRequestDecoder())
      pipeline.addLast("encoder", new HttpResponseEncoder())
      pipeline.addLast("handler", new HttpServerHandler())
    }
  }
}

class HttpServerHandler extends SimpleChannelInboundHandler[Object] {
  override def channelRead0(ctx: ChannelHandlerContext, msg: Object): Unit = {
    if (msg.isInstanceOf[HttpRequest]) {
      val request = msg.asInstanceOf[HttpRequest]
      val response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
        HttpResponseStatus.OK,
        Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8))

      ctx.writeAndFlush(response)
    }

    if (msg.isInstanceOf[HttpContent]) {

      if (msg.isInstanceOf[LastHttpContent]) {
        val response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
          HttpResponseStatus.OK,
          Unpooled.copiedBuffer("foo bar", CharsetUtil.UTF_8))

        ctx.writeAndFlush(response)
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE)
      }
    }
  }
}

class HttpAggregatorInitializer(isClient: Boolean) extends ChannelInitializer[Channel] {
  override def initChannel(ch: Channel): Unit = {
    val pipeline = ch.pipeline()
    if (isClient) {
      pipeline.addLast("codec", new HttpClientCodec())
    } else {
      pipeline.addLast("codec", new HttpServerCodec())
    }

    pipeline.addLast("aggregator", new HttpObjectAggregator(512 * 1024))
  }
}

class HttpCompressionInitializer(isClient: Boolean) extends ChannelInitializer[Channel] {
  override def initChannel(ch: Channel): Unit = {
    val pipeline = ch.pipeline()
    if (isClient) {
      pipeline.addLast("codec", new HttpClientCodec())
      pipeline.addLast("decompressor", new HttpContentDecompressor())
    } else {
      pipeline.addLast("codec", new HttpServerCodec())
      pipeline.addLast("decompressor", new HttpContentCompressor())
    }
  }
}

class HttpsCodecInitializer(context: SslContext, isClient: Boolean) extends ChannelInitializer[Channel] {
  override def initChannel(ch: Channel): Unit = {
    val pipeline = ch.pipeline()
    val engine = context.newEngine(ch.alloc())

    pipeline.addFirst("ssl", new SslHandler(engine))

    if (isClient) {
      pipeline.addLast("codec", new HttpClientCodec())
    } else {
      pipeline.addLast("codec", new HttpServerCodec())
    }
  }
}

object HttpServer extends App {
  val bossGroup = new NioEventLoopGroup
  val workerGroup = new NioEventLoopGroup
  try {
    val b = new ServerBootstrap
    b.group(bossGroup, workerGroup)
      .channel(classOf[NioServerSocketChannel])
      .childHandler(new HttpPipelineInitializer(false))

    val f = b.bind(8080).sync
    f.channel.closeFuture.sync
  } finally {
    workerGroup.shutdownGracefully()
    bossGroup.shutdownGracefully()
  }
}

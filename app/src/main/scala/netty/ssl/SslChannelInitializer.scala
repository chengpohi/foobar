package netty.ssl

import io.netty.channel.{Channel, ChannelInitializer}
import io.netty.handler.ssl.{SslContext, SslHandler}

class SslChannelInitializer(context: SslContext, startTls: Boolean)
    extends ChannelInitializer[Channel] {
  override def initChannel(ch: Channel): Unit = {
    val engine = context.newEngine(ch.alloc())
    ch.pipeline().addFirst("ssl", new SslHandler(engine, startTls))
  }
}

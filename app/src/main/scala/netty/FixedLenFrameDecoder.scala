package netty

import java.util

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

class FixedLenFrameDecoder(fixedLength: Int) extends ByteToMessageDecoder{
  override def decode(ctx: ChannelHandlerContext, in: ByteBuf, out: util.List[AnyRef]): Unit = {
    while(in.readableBytes() > fixedLength) {
      out.add(in.readBytes(fixedLength))
    }
  }
}

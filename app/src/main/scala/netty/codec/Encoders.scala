package netty.codec

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.{MessageToByteEncoder, MessageToMessageEncoder}

class ShortToByteEncoder extends MessageToByteEncoder[Short] {
  override def encode(ctx: ChannelHandlerContext, msg: Short, out: ByteBuf): Unit = {
    out.writeShort(msg)
  }
}

class IntegerToStringEncoder extends MessageToMessageEncoder[Int] {
  override def encode(ctx: ChannelHandlerContext, msg: Int, out: java.util.List[AnyRef]): Unit = {
    out.add(String.valueOf(msg))
  }
}

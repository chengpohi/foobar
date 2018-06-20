package netty.codec

import java.util

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.{ByteToMessageDecoder, MessageToMessageDecoder, ReplayingDecoder, TooLongFrameException}

/**
  * see more: HttpObjectAggregator
  * @param fixedLength
  */
class FixedLenFrameDecoder(fixedLength: Int) extends ByteToMessageDecoder {
  override def decode(ctx: ChannelHandlerContext, in: ByteBuf, out: util.List[AnyRef]): Unit = {
    while (in.readableBytes() > fixedLength) {
      out.add(in.readBytes(fixedLength))
    }
  }
}

class ToIntegerDecoder extends ByteToMessageDecoder {
  override def decode(ctx: ChannelHandlerContext, in: ByteBuf, out: util.List[AnyRef]): Unit = {
    while (in.readableBytes() >= 4) {
      val i: Any = in.readInt()
      out.add(i.asInstanceOf[AnyRef])
    }
  }
}

class ToIntegerDecoder2 extends ReplayingDecoder[Void] {
  override def decode(ctx: ChannelHandlerContext, in: ByteBuf, out: util.List[AnyRef]): Unit = {
    val i: Any = in.readInt()
    out.add(i.asInstanceOf[AnyRef])
  }
}

class IntegerToStringDecoder extends MessageToMessageDecoder[Integer] {
  override def decode(ctx: ChannelHandlerContext, msg: Integer, out: util.List[AnyRef]): Unit = {
    out.add(msg.toString)
  }
}

class SafeByteToMessageDecoder extends ByteToMessageDecoder {
  val MAX_FRAME_SIZE = 3
  override def decode(ctx: ChannelHandlerContext, in: ByteBuf, out: util.List[AnyRef]): Unit = {
    val readable = in.readableBytes()

    if (readable > MAX_FRAME_SIZE) {
      in.skipBytes(readable)
      throw new TooLongFrameException("Frame too big")
    }

    out.add(in.readBytes(readable))
  }
}

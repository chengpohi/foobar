package netty.codec

import java.util

import io.netty.buffer.ByteBuf
import io.netty.channel.{ChannelHandlerContext, CombinedChannelDuplexHandler}
import io.netty.handler.codec.{ByteToMessageDecoder, MessageToByteEncoder}

class ByteToCharDecoder extends ByteToMessageDecoder {
  override def decode(ctx: ChannelHandlerContext, in: ByteBuf, out: util.List[AnyRef]): Unit = {
    while(in.readableBytes() >= 2) {
      val c: Any = in.readChar()
      out.add(c.asInstanceOf[AnyRef])
    }
  }
}

class CharToByteEncoder extends MessageToByteEncoder[Character] {
  override def encode(ctx: ChannelHandlerContext, msg: Character, out: ByteBuf): Unit = {
    out.writeChar(msg.toInt)
  }
}


class CombinedCoders extends CombinedChannelDuplexHandler[ByteToCharDecoder, CharToByteEncoder](new ByteToCharDecoder, new CharToByteEncoder){
}

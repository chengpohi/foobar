package netty

import io.netty.buffer.{ByteBuf, Unpooled}
import io.netty.channel.embedded.EmbeddedChannel
import org.scalatest.{FlatSpec, Matchers}

class EmbeddedChannelTest extends FlatSpec with Matchers {

  it should "decode" in {
    val buf = Unpooled.buffer()
    (0 to 9).foreach(i => {
      buf.writeByte(i)
    })

    val input = buf.duplicate()
    val embeddedChannel = new EmbeddedChannel(new FixedLenFrameDecoder(3))

    embeddedChannel.writeInbound(input.retain)
    embeddedChannel.finish

    val read: ByteBuf = embeddedChannel.readInbound()

    buf.readSlice(3) should equal(read)

  }
}

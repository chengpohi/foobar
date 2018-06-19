package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.TooLongFrameException;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class FixedLengthFrameDecoderTest {

    @Test
    public void shouldDecodeFrame() {
        ByteBuf buffer = Unpooled.buffer();

        for (int i = 0; i < 9; i++) {
            buffer.writeByte(i);
        }

        ByteBuf input = buffer.duplicate();
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));

        embeddedChannel.writeInbound(input.retain());
        embeddedChannel.finish();

        ByteBuf read = embeddedChannel.readInbound();

        assertEquals(buffer.readSlice(3), read);
        read.release();

        read = embeddedChannel.readInbound();

        assertEquals(buffer.readSlice(3), read);
        read.release();

        read = embeddedChannel.readInbound();

        assertEquals(buffer.readSlice(3), read);
        read.release();

        assertNull(embeddedChannel.readInbound());
        buffer.release();
    }

    @Test
    public void shouldFrameDecodeFalse() {
        ByteBuf buffer = Unpooled.buffer();

        for (int i = 0; i < 9; i++) {
            buffer.writeByte(i);
        }

        ByteBuf input = buffer.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));

        assertFalse(channel.writeInbound(input.readBytes(2)));
        assertTrue(channel.writeInbound(input.readBytes(7)));
    }

    @Test
    public void shouldEncode() {
        ByteBuf buffer = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buffer.writeInt(i * -1);
        }

        EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());

        assertTrue(channel.writeOutbound(buffer));
        assertTrue(channel.finish());

        for (int i = 0; i < 9; i++) {
            int t = channel.readOutbound();
            assertEquals(i, t);
        }

        assertNull(channel.readOutbound());
    }

    @Test
    public void shouldTestTooLongException() {
        ByteBuf buffer = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buffer.writeInt(i * -1);
        }

        ByteBuf input = buffer.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(new FrameChunkDecoder(3));

        assertTrue(channel.writeInbound(input.readBytes(2)));
        try {
            channel.writeInbound(input.readBytes(4));
            assertTrue(false);
        } catch (TooLongFrameException ex) {
            assertTrue(true);
        }

        assertTrue(channel.writeInbound(input.readBytes(3)));
        channel.finish();

    }
}

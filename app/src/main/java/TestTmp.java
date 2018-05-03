import java.nio.CharBuffer;

public class TestTmp {
    public static void main(String[] args) {
        CharBuffer buffers = CharBuffer.allocate(1000);
        buffers.flip();
        buffers.put('c');
        buffers.put('a');
        buffers.put(7, 'd');
        buffers.remaining();
        buffers.hasRemaining();
        buffers.compact();

        System.out.println("");
    }

}


import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class JavaNIO {
    public static void printBuffer(ByteBuffer buffer) {
        // 버퍼에 들어있는 데이터 출력
        for(int i=0; i<buffer.limit() -1; i++)
            System.out.print(buffer.get(i) + ", ");
        System.out.println(buffer.get(buffer.limit() -1));
        // 버퍼 상태 출력
        System.out.print("position: " + buffer.position() + ", ");
        System.out.print("limit: " + buffer.limit() + ", ");
        System.out.println("capacity: " + buffer.capacity());
    }

    public static void main(String[] args) {
        // buffer creation - allocation
        ByteBuffer buffer = ByteBuffer.allocate(20); // non-Direct buffer JVM
        ByteBuffer bufferD = ByteBuffer.allocateDirect(20); // Direct bufffer Windows
        printBuffer(buffer);
        CharBuffer cbuffer = CharBuffer.allocate(30); // 30 char 사이즈로 버퍼생성
        CharBuffer cbuuferD = ByteBuffer.allocateDirect(30).asCharBuffer(); // 30 char 다이렉트 버퍼 생성
        // buffer creation - wrapping
        byte[] bytes = new byte[20];
        ByteBuffer buffer2 = ByteBuffer.wrap(bytes);
        // Buffer writing/reading
        buffer.put((byte) 8);
        buffer.put((byte) 12);
        buffer.put((byte) 13);
        printBuffer(buffer);
        buffer.put((byte) 14);
        buffer.put((byte) 16);
        printBuffer(buffer);
        buffer.flip(); // writing --> reading
        printBuffer(buffer);
        buffer.get(new byte[3]);
        printBuffer(buffer);
        buffer.mark(); // 현재 position 위치에 표시를 남김 왜? mark 위치로 돌아갈 수 있게
        buffer.get(new byte[2]);
        printBuffer(buffer);
        buffer.reset(); // position을 mark 위치로 이동시킨다.
        printBuffer(buffer);
        buffer.compact();
        printBuffer(buffer);
        buffer.put((byte) 21);
        buffer.put((byte) 23);
        buffer.put((byte) 24);
        buffer.put((byte) 26);
        printBuffer(buffer);
        buffer.flip();
        buffer.get(new byte[4]);
        printBuffer(buffer);
        buffer.rewind(); // position을 맨앞으로 보냉 읽었던것을 다시 읽어올수 있게
        printBuffer(buffer);
        buffer.clear();
        printBuffer(buffer);

    }
}

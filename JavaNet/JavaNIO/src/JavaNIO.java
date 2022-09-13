import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

public class JavaNIO {
    public static void main(String[] args) {
        // Buffer creation
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(100); // Direct buffer 운영체제가 관리하는 버퍼
        ByteBuffer byteBuffer1 = ByteBuffer.allocate(100); // non-Direct buffer 자바 가상머신이 관리하는 버퍼
        IntBuffer intBuffer = IntBuffer.allocate(30);
        System.out.println(intBuffer.capacity());
        CharBuffer charBuffer = ByteBuffer.allocateDirect(100).asCharBuffer(); // 바이트 버퍼를 캐릭터 버퍼로 변환
        // wrap method
        byte[] bytes = new byte[1024];
        ByteBuffer byteBuffer2 = ByteBuffer.wrap(bytes); // array -> buffer
        char[] chars = new char[100];
        CharBuffer charBuffer1 = CharBuffer.wrap(chars);
        // Buffer class`s methods
        ByteBuffer buf = ByteBuffer.allocate(10);
        System.out.println("*position: " + buf.position());
        printBuffer(buf);
        //buf.rewind();
        buf.put((byte) 5);
        buf.put((byte) 7);
        buf.put((byte) 8);
        buf.put((byte) 3);
        printBuffer(buf);
        buf.flip();  // 쓰기 -> 읽기
        printBuffer(buf);
        int a = buf.get(); // 현재 포지션익 가리키는 값을 읽어옴
        a = buf.get();
        printBuffer(buf);
        buf.mark(); // 현재 position 위치에 마크 표시
        a = buf.get();
        a = buf.get();
        printBuffer(buf);
        buf.reset();
        printBuffer(buf);
        buf.rewind();
        printBuffer(buf);
        buf.put((byte) 13);
        printBuffer(buf);
        buf.flip();
        printBuffer(buf);
    }
    private static void printBuffer(ByteBuffer buf) {
        for(int i=0; i<buf.limit(); i++)
            System.out.print(buf.get(i) + " ");
        System.out.print("\n");
        System.out.println("position : " + buf.position());
        System.out.println("limit: " + buf.limit());
        System.out.println("capacity: " + buf.capacity());
    }
    private static void SpeedTest() throws Exception {
        // NIO의 API에서 파일의 경로를 지정하기 위해 Path를 사용
        Path from = Paths.get("test.jpg");
        Path to1 = Paths.get("testnon.jpg");
        Path to2 = Paths.get("testdirect.jpg");

        long size = Files.size(from);

        FileChannel fileChannel_from = FileChannel.open(from); // from file open
        FileChannel fileChannel_to1 = FileChannel.open(to1, // to1 file open
                EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.WRITE)); // option 설정
        FileChannel fileChannel_to2 = FileChannel.open(to2, // to2 file open
                EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.WRITE));

        ByteBuffer nonDirectBuffer = ByteBuffer.allocate((int) size);
        ByteBuffer directBuffer = ByteBuffer.allocateDirect((int) size);

        long start, end;

        start = System.nanoTime(); // 시간체크

        for (int i = 0; i < 100; i++) {
            fileChannel_from.read(nonDirectBuffer);  // 버퍼에 기록
            nonDirectBuffer.flip();  // 모드 변경
            fileChannel_to1.write(nonDirectBuffer);  // 버퍼에서 읽기
            nonDirectBuffer.clear(); // 초기화
        }

        end = System.nanoTime();

        System.out.println("넌 다이렉트: " + (end - start) + " ns");

        start = System.nanoTime();

        for (int i = 0; i < 100; i++) {
            fileChannel_from.read(directBuffer);
            directBuffer.flip();
            fileChannel_to2.write(directBuffer);
            directBuffer.clear();
        }

        end = System.nanoTime();
        System.out.println("다이렉트:\t: " + (end - start) + " ns");

        fileChannel_from.close();
        fileChannel_to1.close();
        fileChannel_to2.close();

    }
}


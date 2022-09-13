import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import static java.lang.Thread.sleep;

public class TimeClientChannel {
    private static void displayBuffer(ByteBuffer buf) {
        System.out.println("Capacity: "+buf.capacity()+"Limit: "+buf.limit()+"Position: "+buf.position());
    }
    public static void main(String[] args) {
        SocketAddress address = new InetSocketAddress("127.0.0.1", 5002);
        try {
            SocketChannel channel = SocketChannel.open(address);
            channel.configureBlocking(true); // read(),write() 메소드를 블록킹으로 동작
            ByteBuffer buf = ByteBuffer.allocate(64);
            displayBuffer(buf);
            int readBytes = channel.read(buf); // channel --> buf 채널 데이터 수신해서 버퍼에 기록
            while (readBytes != -1) { // 수신한 데이터가 있다.
                buf.flip(); // 기록상태 --> 읽기상태
                displayBuffer(buf);
                while (buf.hasRemaining()) System.out.print((char)buf.get()); // position 위치의 바이트 하나를 문자로 출력
                System.out.println();
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                buf.clear();
                readBytes = channel.read(buf); // 채널에서 수신한 데이터의 바이트크기 or -1(정상종료) or IOException(비정상종료)
                System.out.println("Read bytes: " + readBytes);
                displayBuffer(buf);
            }
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class TimeServer {
    public static void main(String[] args) {
        System.out.println("Time server is started.");
        try {
            ServerSocketChannel sschannel = ServerSocketChannel.open();
            // Blocking/Nonblocking mode 선택할 수 있음
            sschannel.configureBlocking(true); // Blocking mode
            sschannel.socket().bind(new InetSocketAddress(5000));
            while(true) {
                System.out.println("Waiting for Clients....");
                SocketChannel client = sschannel.accept();
                InetSocketAddress clientAddress = (InetSocketAddress) client.getRemoteAddress();
                System.out.println("Connected." + clientAddress.getAddress() + " "
                        + clientAddress.getPort());
                // buffer creation
                ByteBuffer buf = ByteBuffer.allocate(64);
                for(int i=0; i<10; i++) { // 컴퓨터에 있는 현재 날짜와 시간을 가져온다
                    String message = "Data: " + new Date(System.currentTimeMillis());
                    buf.clear(); // 이전에 입력한 데이터 삭제. position = 0. limit = capacity
                    buf.put(message.getBytes());
                    buf.flip(); // writing --> reading mode
                    while (buf.hasRemaining()) client.write(buf);
                    System.out.println("Sent: " + message);
                    sleep(1000);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

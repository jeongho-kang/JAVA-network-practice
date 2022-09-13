import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TimeClient {
    public static void main(String[] args) {
        System.out.println("Time client is started.");
        try {
            SocketAddress address = new InetSocketAddress("127.0.0.1" ,5000);
            SocketChannel schannel = SocketChannel.open(address);
            System.out.println("Connected.");
            // Buffer creation
            ByteBuffer buffer = ByteBuffer.allocate(64);
            int bytes = schannel.read(buffer); // data를 receving --> buffer writing
            System.out.println(bytes + " bytes are received.");
            while(bytes != -1) {
                buffer.flip(); // writing --> reading
                //while ( buffer.hasRemaining()) System.out.println((char) buffer.get());
                byte[] data = new byte[buffer.limit()];
                buffer.get(data,0,buffer.limit());
                String msg = new String(data);
                System.out.println(msg);
                System.out.println();
                sleep(1000);
                buffer.clear();
                bytes = schannel.read(buffer);
                System.out.println(bytes + " bytes are received.");

            }

            } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

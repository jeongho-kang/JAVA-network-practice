import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class UDPReceiver {
    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 9005);
        DatagramChannel channel = DatagramChannel.open();
        channel.configureBlocking(true);
        channel.socket().bind(address);

        ByteBuffer buffer = ByteBuffer.allocate(64);
        while(true) {
            channel.receive(buffer);
            buffer.flip();
            byte[] data = new byte[buffer.limit()];
            buffer.get(data, 0,buffer.limit());
            String msg = new String(data);
            if(msg.equalsIgnoreCase("quit")) break;
            System.out.println(msg);
            buffer.clear();
        }
    }
}

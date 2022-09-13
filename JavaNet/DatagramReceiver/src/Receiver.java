import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Receiver {
    public static void main(String[] args) {
        InetSocketAddress address = new InetSocketAddress("localhost", 7008);
        try {
            DatagramChannel channel = DatagramChannel.open();
            channel.bind(address);
            ByteBuffer buffer = ByteBuffer.allocate(200);
            while(true) {
                channel.receive(buffer);
                buffer.flip();
                while (buffer.hasRemaining()) System.out.print((char)buffer.get());
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

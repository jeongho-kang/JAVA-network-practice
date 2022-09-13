import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        SocketAddress address = new InetSocketAddress("localhost",3000);
        SocketChannel channel = SocketChannel.open(address); // connect request
        System.out.println("Connected to server.");
        // 서버에게 메시지 전송
        String[] message = new String[]{"Hello server!", "Good day!", "Bye."};
        for(int i =0; i< message.length; i++) {
            byte [] msg = message[i].getBytes();
            ByteBuffer buffer = ByteBuffer.wrap(msg);
            channel.write(buffer); // 서버에게 메시지 전송
            System.out.println("Sent : " + message[i]);
            buffer.clear();
            Thread.sleep(5000);
        }
        channel.close();
    }
}

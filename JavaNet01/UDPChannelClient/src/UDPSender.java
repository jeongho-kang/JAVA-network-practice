import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UDPSender {
    private static Scanner scn = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        ByteBuffer buffer = ByteBuffer.allocate(64);
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 9005);
        while(true) {
            String msg;
            System.out.println("Message: ");
            msg = scn.nextLine();
            buffer.put(msg.getBytes());
            buffer.flip(); // 기록하느것을 읽는걸로 바꿈
            channel.send(buffer, address);
            buffer.clear();
            if(msg.equalsIgnoreCase("quit")) break;
        }
    }
}

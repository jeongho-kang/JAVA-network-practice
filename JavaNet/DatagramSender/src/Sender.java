import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sender {
    private static List<String> users = new ArrayList<>(Arrays.asList("James","Bob","Tom","Jerry","Sam"));
    public static void main(String[] args) {
        try {
            DatagramChannel channel = DatagramChannel.open();
            ByteBuffer buffer = ByteBuffer.allocate(200);
            for(String name : users) {
                name += "\n";
                buffer.put(name.getBytes());
                buffer.flip();
                channel.send(buffer, new InetSocketAddress("localhost", 7008));
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

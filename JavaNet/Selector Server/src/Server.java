import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {
    public static void main(String[] args) throws IOException {
        // 셀렉터 생성
        Selector selector = Selector.open();
        // 서버소켓 채널 생성하고 셀렉터에 등록하기
        ServerSocketChannel sschannel = ServerSocketChannel.open();
        sschannel.configureBlocking(false); // 서버에 있는 소켓을 넌블로킹으로 동작
        sschannel.bind(new InetSocketAddress(7100));
        SelectionKey sskey = sschannel.register(selector, SelectionKey.OP_ACCEPT, null);
        while (true) {
            System.out.println("Server's selector is waiting ...");
            int noOfkeys = selector.select(); // 적합한 키를 선택
            System.out.println("Number of keys: " + noOfkeys);
            Set keys = selector.selectedKeys(); // 집합으로 key를 받음
            Iterator iter = keys.iterator(); // 순환자 생성
            while (iter.hasNext()) { // iterator 가 가리키는 키가 있는동안
                SelectionKey key = (SelectionKey) iter.next();
                if (key.isAcceptable()) {
                    SocketChannel client = sschannel.accept();
                    client.configureBlocking(false);
                    SelectionKey clientkey = client.register(selector, SelectionKey.OP_READ, null);
                    System.out.println("Client: " + client);
                } else if (key.isReadable()) { // 수신 가능한 키
                    SocketChannel client = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(100);
                    client.read(buffer);
                    String message = new String(buffer.array());
                    System.out.println("recieved: " + message);
                    if (message.equals("Bye.")) client.close();
                }
                iter.remove(); // 현재 가리키는 것 삭제
            }
        }
    }
}

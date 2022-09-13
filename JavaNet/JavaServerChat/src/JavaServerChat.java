import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Vector;


public class JavaServerChat {
    static Vector connections = new Vector<>(); // 연결된 클라이언트르 저장하는 Vecotr
    private static String recvMsg(SocketChannel channel) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024); // ByteBuffer의 용량을 1024로 할당함
            String msg = "";
            while(channel.read(buffer) > 0) {
                char byteRead = 0x00;
                buffer.flip();
                while (buffer.hasRemaining()) { // buffer로부터 글자를 하나씩 읽어옴
                    byteRead = (char) buffer.get();
                    if(byteRead == 0x00) break;
                    msg += byteRead;
                }
                if(byteRead == 0x00) break;
            }
            return msg;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    private static void show(String msg) {
        System.out.println(msg);
    }
    public static void main(String[] args) {
        show("끝말잇기 게임서버 시작...");
        try {
            ServerSocketChannel sschannel = ServerSocketChannel.open();
            sschannel.configureBlocking(true);
            sschannel.socket().bind(new InetSocketAddress(3005));
            boolean running = true;
            while(running) {
                show("사용자 접속 대기....");
                SocketChannel socket = sschannel.accept();
                show("사용자 접속 >> " + recvMsg(socket));
                connections.add(socket);
                while(true) {
                    show("Received: " + recvMsg(socket));
                    if(recvMsg(socket).equals("quit")) {
                        running = false;
                        break;
                    }
                    else show("Received: " + recvMsg(socket));
                }
            }
        } catch (IOException e) { // 해당 포트에 접속하지 못했을떄.
            e.printStackTrace();
        }
    }
}

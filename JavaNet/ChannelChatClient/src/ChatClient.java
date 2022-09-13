import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class ChatClient {
    private static void sendMsg(SocketChannel channel, String msg) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(msg.length() + 1);
            buffer.put(msg.getBytes());
            buffer.put((byte) 0x00); // 메세지의 끝을 가리키는 mark
            buffer.flip(); // 기록 -> 읽기
            while (buffer.hasRemaining()) {

                channel.write(buffer);
                show("Sent: " + msg);
            }
        }       catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static String recvMsg(SocketChannel channel) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
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
        SocketAddress address = new InetSocketAddress("127.0.0.1", 3005);
        try {
            SocketChannel socket = SocketChannel.open(address);
            show("Client is connected.");
            String message;
            Scanner sc = new Scanner(System.in);
            while(true) {
                show("Received: " + recvMsg(socket));
                System.out.print(">> ");
                message = sc.nextLine();
                sendMsg(socket, message);
                if(message.equals("quit")) break;
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

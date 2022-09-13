import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class UDPServer {
    UDPServer() {
        System.out.println("Echo UDPServer is started.");
        try (DatagramSocket socket = new DatagramSocket(9000)) {
            while(true) {
                byte[] data = new byte[1024]; // 데이터그램 패킷에 담을 데이터 공간
                // 클라이언트가 보낸 데이터그램 패킷을 받을 서버쪽 데이터그램 패킷
                DatagramPacket packet = new DatagramPacket(data,data.length);
                // 데이터그램 수신
                socket.receive(packet); // 블록모드로 동작 즉, 수신한 데이터가 올떄까지 대기 하는 상태
                String message = new String(packet.getData());
                if(message.trim().equalsIgnoreCase("quit")) break;
                System.out.println("Received: " + message.trim() + " from: "
                        + packet.getAddress() + " " + packet.getPort());
                InetAddress address = packet.getAddress(); // 데이터그램 패킷을 보낸 쪽의 주소
                int port = packet.getPort();
                byte[] sendData = message.getBytes();
                DatagramPacket packet1 = new DatagramPacket(sendData,sendData.length,address,port);
                socket.send(packet1);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new UDPServer(); // UDP서버 생성
    }
}

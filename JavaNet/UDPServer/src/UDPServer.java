import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;

public class UDPServer {
    public UDPServer() {
        System.out.println("UDPServer is started");
        try {
            // 데이타그램 소켓 생성
            DatagramSocket socket = new DatagramSocket(5005);
            while (true) {
                // 데이터그램 패킷 수신하기
                byte[] message = new byte[1024]; // 패킷안에 데이터는 바이트 스트림(배열)로 들어있음.
                DatagramPacket packet = new DatagramPacket(message, message.length);
                socket.receive(packet); // 소켓으로 들어오는 패킷을 packet에 저장한다. 수신할 패킷이 없으면 대기
                String recvData = new String(packet.getData());
                System.out.println("Received form client: ["+ recvData + "]\nFrom :"
                        + packet.getAddress() + ":" + packet.getPort());
                // 데이터그램 패킷 송신하기
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                byte[] message2 = recvData.getBytes(); // 받은 데이타를 바이트로 변환
                DatagramPacket packet2 = new DatagramPacket(message2, message2.length, address, port);
                socket.send(packet2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new UDPServer();
    }
}

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {
    public UDPClient() {
        Scanner sc = new Scanner(System.in);
        System.out.println("UDPClient is started");
        try {
            DatagramSocket socket = new DatagramSocket();
            // 목적지 주소와 포트번호를 가지고 데이터(바이트 스트림) 데이터그램패킷 생성
            InetAddress address = InetAddress.getByName("localhost"); // Host name --> IP address
            byte[] message;
            while (true) {
                System.out.print("Enter a message : ");
                String sendData = sc.nextLine();
                message = sendData.getBytes(); // String --> byte()
                if(sendData.equals("quit")) break;
                DatagramPacket packet = new DatagramPacket(message, message.length, address, 5005);
                socket.send(packet);
            // 수신하기
                byte[] message2 = new byte[1024];
                DatagramPacket packet2 = new DatagramPacket(message2, message2.length);
                socket.receive(packet2);
                String recvData = new String(packet2.getData());
                System.out.println("Received form Server: ["+ recvData + "]\nFrom :"
                        + packet.getAddress() + ":" + packet.getPort());

            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  static void main(String[] args) {
        new UDPClient();

    }
}

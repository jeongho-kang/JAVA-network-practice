import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UDPEngClient {
    UDPEngClient() {
        System.out.println("Echo UDPClient is started.");
        System.out.println("Are you ready for quiz test(yes/no)? ");
        Scanner sc = new Scanner(System.in);
        // 포트번호 없이 만들면 임의의 포트를 생성한다.
        try(DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName("localhost");
            byte[] data;
            while(true) {
                System.out.print("Send Message : ");
                String message = sc.nextLine();
                // String --> byte[]로 변경해서 담아야함
                data = message.getBytes();
                DatagramPacket packet = new DatagramPacket(data,data.length,address,9000);
                socket.send(packet); // 데이터그램 전송하기
                if(message.equalsIgnoreCase("quit")) break;
                // 서버로부터 데이터그램 수신하기
                byte[] recvData = new byte[1024];
                DatagramPacket packet1 = new DatagramPacket(recvData,recvData.length);
                socket.receive(packet1);
                String recvMessage = new String(packet1.getData()).trim();
                System.out.println("Received: " + recvMessage.trim() + " from: "
                        + packet.getSocketAddress());

            }
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new UDPEngClient();
    }
}

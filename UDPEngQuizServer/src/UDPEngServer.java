import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

public class UDPEngServer extends VocaManager{

    UDPEngServer() {
        super();
        int score = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Echo UDPServer is started.");
        VocaManager word = new VocaManager("Voca1800.txt");
        word.addWords();
        System.out.print("생성할 퀴즈 갯수 : ");
        int quantity = sc.nextInt();
        word.create_quiz();
        try (DatagramSocket socket = new DatagramSocket(9000)) {
            while(true) {
                byte[] data = new byte[1024]; // 데이터그램 패킷에 담을 데이터 공간
                // 클라이언트가 보낸 데이터그램 패킷을 받을 서버쪽 데이터그램 패킷
                DatagramPacket packet = new DatagramPacket(data,data.length);
                // 데이터그램 수신
                socket.receive(packet); // 블록모드로 동작 즉, 수신한 데이터가 올떄까지 대기 하는 상태
                String message = new String(packet.getData());
                if (message.trim().equalsIgnoreCase("quit")) break;
                System.out.println("Received : " + message.trim());
                InetAddress address = packet.getAddress(); // 데이터그램 패킷을 보낸 쪽의 주소
                int port = packet.getPort();
                while(true) {
                    for (int i = 0; i < quantity; i++) {
                        String message1 = question[e][i];
                        byte[] sendData = message1.getBytes();
                        DatagramPacket packet1 = new DatagramPacket(sendData, sendData.length, address, port);
                        socket.send(packet1);
                        socket.receive(packet);
                        message = new String(packet.getData());
                        System.out.println("Received : " + message.trim());
                        if (message.trim().contains(answer[e][i])) score += 10;
                    } System.out.println("최종점수 : " + score);
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new UDPEngServer();
    }
}

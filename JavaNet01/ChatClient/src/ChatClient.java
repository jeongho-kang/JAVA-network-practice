import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    final static int serverPort = 3005;
    public static void main(String[] args) throws IOException {
        Scanner scn = new Scanner(System.in);
        InetAddress ipaddr = InetAddress.getByName("localhost");
        Socket s = new Socket(ipaddr, serverPort);
        System.out.println("Connection OK!");
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        System.out.print("User name: ");
        String name = scn.nextLine();
        dos.writeUTF(name); // 서버에게 user name 전송
        Thread sendThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.print("Msg(who#message) : ");
                    String msg = scn.nextLine();
                    if (msg.equals("BYE")) break;
                    try {
                        dos.writeUTF(msg); // 서버에 메시지 전송
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            });
            Thread recvThread = new Thread(new Runnable() { // 데이터 수신 쓰레드
                @Override
                public void run() {
                    while(true) {
                        String msg = null;
                        try {
                            msg = dis.readUTF();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(msg == null) break; // 서버의 접속이 해제된 경우,서버에서 소켓을 닫은 경우
                        System.out.println("Received : " + msg);
                    }
                    try {
                        dis.close();
                        dos.close();
                        s.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            sendThread.start();
            recvThread.start();

    }
}



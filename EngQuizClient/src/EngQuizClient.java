import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class EngQuizClient {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        Socket s = new Socket("127.0.0.1", 3005);
        System.out.println("Client is connedted to the English Quiz server.");
        // 수신스트림 송신스트림 생성
        DataInputStream dis = new DataInputStream(s.getInputStream()); // 수신스트림
        DataOutputStream dos = new DataOutputStream(s.getOutputStream()); // 송신스트림
        // 클라이언트 이름
        System.out.print("Your name : ");
        String name = sc.next();
        dos.writeUTF(name); // 서버로 송신
        System.out.println("Are you ready for quiz test(yes/no)? ");
        String quiz = sc.next();
        dos.writeUTF(quiz);
            Thread sendMsg = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        String message = sc.next();
                        try {
                            dos.writeUTF(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            Thread recvMsg = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            String message = dis.readUTF();
                            if (message.equals("logout")) break;
                            System.out.println(message);
                        } catch (IOException e) {
                            try {
                                dis.close();
                                dos.close();
                                s.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            });
            // start threads
            sendMsg.start();
            recvMsg.start();
        }
    }


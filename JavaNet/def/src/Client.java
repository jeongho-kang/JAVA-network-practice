import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        Socket s = new Socket("127.0.0.1", 3005);
        System.out.println("Connection is successful.");
        // create input and output stream object
        DataInputStream dis = new DataInputStream(s.getInputStream()); // 수신스트림
        DataOutputStream dos = new DataOutputStream(s.getOutputStream()); // 송신스트림
        // client's name
        System.out.print("Your name : ");
        String name = sc.next();
        dos.writeUTF(name);

        Thread sendMsg = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String message = sc.nextLine();  // who#msg 형식
                    try {
                        dos.writeUTF(message);
                        if (message.equals("logout")) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
        Thread recvMsg = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String message = dis.readUTF();
                        if(message == null) break;
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





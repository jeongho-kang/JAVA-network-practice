import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

public class ChatServer {

    static Vector<ClientChatting> clinets = new Vector<>();

    public static void main(String[] args) throws IOException {
        Scanner scn = new Scanner(System.in);
        ServerSocket ss = new ServerSocket(3005);
        Socket s; // 클라이언트 socket
        while(true) {
            System.out.println("Chat Server is waiting...");
            s = ss.accept();
            System.out.println("New client is arrived : " + s);
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            String name = dis.readUTF();
            System.out.println("Client`s User name: " + name);
            // 접속한 클라이언트와 채팅을 처리할 객체 생성
            ClientChatting cc = new ClientChatting(s, name, dis,dos);
            clinets.add(cc); // 벡터에 등록
            Thread thread = new Thread(cc);
            thread.start();

            /*
            while(true) {
                String msg = dis.readUTF();
                if(msg.equals("BYE") || msg == null) break;
                System.out.println("Client`s msg : " + msg);
                System.out.print("Msg : ");
                msg = scn.nextLine();
                dos.writeUTF(msg);
            }
             */
        }
    }
}

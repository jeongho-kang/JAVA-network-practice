import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ClientChatting  implements Runnable{
    Scanner scn = new Scanner(System.in);
    private String name;
    final DataInputStream dis;
    final DataOutputStream dos;
    private Socket s;
    private boolean isSignin;
    public ClientChatting(Socket s, String name, DataInputStream dis,
                          DataOutputStream dos) {
        this.name = name;
        this.dis = dis;
        this.dos = dos;
        this.s = s;
        isSignin = true;
    }
    @Override
    public void run() {
        System.out.println("Chat Thread is running");
        String receivedMsg;
        while (true) {
            try {
                receivedMsg = dis.readUTF(); // 클라이언트부터 메세지 수신
                System.out.println("received: " + receivedMsg + "from " + name);
                if(!receivedMsg.contains("#")) continue; // #이 포함되어있지 않으면 전달 불가 #은 수신처
                StringTokenizer st = new StringTokenizer(receivedMsg, "#");
                String who = st.nextToken(); // 수신자
                String msg = st.nextToken(); // 메시지
                // 배열에서 수신자 객체 탐색하기
                for(ClientChatting cc : ChatServer.clinets) {
                    if(cc.name.equals(who) && cc.isSignin) {
                        cc.dos.writeUTF(name + " >> " + msg);
                        break; // for문 탈출
                    }
                }
            } catch (SocketException e) {
                try {
                    s.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                dis.close();
                dos.close();
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            }
        }
    }

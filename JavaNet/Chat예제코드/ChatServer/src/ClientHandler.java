import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ClientHandler implements Runnable {
    private Scanner sc = new Scanner(System.in);
    String name;
    final DataInputStream dis;
    final DataOutputStream dos;
    Socket s;
    boolean isLoggedIn;

    public ClientHandler(Socket s, String name, DataInputStream dis, DataOutputStream dos) {
        this.name = name;
        this.dis = dis;
        this.dos = dos;
        this.s = s;
        isLoggedIn = true;
    }
    // 클라이언트에게 받은 메세지를 보고 수신처와 메세지를 분리한 다음 해당 수신쪽으로 메세지 전달하기
    @Override
    public void run() {
        String message;
        while (true) {
            try {
                message = dis.readUTF(); // 메세지 수신
                System.out.println("received : " + message);
                if(message.equals("logout")) {
                    isLoggedIn = false;
                    s.close();
                    break;
                }
                if(!message.contains("#")) continue; // 메세지 형식 오류이므로 다시 시작
                StringTokenizer tokenizer = new StringTokenizer(message, "#");
                String who = tokenizer.nextToken(); // 수신처
                String msg = tokenizer.nextToken(); // 메세지
                // 수신처이름으로 ClientHandler 객체 찾기
                for(ClientHandler handler : ChatServer.ar) {
                    if(handler.name.equals(who) && handler.isLoggedIn) { // 수신처 찾았고 로그인된 상태
                        handler.dos.writeUTF(who + ">> " + msg);
                        while(true) {

                            int lastIndex = message.length()-1; //마지막 문자에 대한 인덱스
                            char lastChar = message.charAt(lastIndex); //마지막 문자


                            if (lastChar == message.charAt(0)){
                                System.out.println(name + "이 성공 !");
                            }
                            else {
                                System.out.println(name + "이 패배 !");
                                break;
                            }


                        }
                        break;
                    }
                }
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
}

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class EngChatting extends EngQuizServer implements Runnable {
    private static Scanner sc = new Scanner(System.in);
    String name;
    final DataInputStream dis;
    final DataOutputStream dos;
    Socket s;
    boolean isLoggedIn;

    public EngChatting(Socket s, String name, DataInputStream dis, DataOutputStream dos) {
        super();
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
        String quiz;
        int score = 0;
        while (true) {
            try {

                message = dis.readUTF(); // 메세지 수신
                if (message.equals("no")) {
                    isLoggedIn = false;
                    s.close();
                    break;
                }
                dos.writeUTF("Quiz test is started");
                    for (int i = 0; i < 10; i++) {
                        System.out.println((i + 1) + "번 문제 : " +
                                question[e][i] + " // " + answer[e][i]);
                        dos.writeUTF(question[e][i]);
                        message = dis.readUTF();
                        System.out.println("received : " + message);
                        if (answer[e][i].contains(message)) score += 10;
                        corrects[e] = score;
                    }
                    System.out.print("Sent to the final score : " + score);
                    dos.writeUTF(String.valueOf(score));

                    try {
                        dis.close();
                        dos.close();
                        s.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}

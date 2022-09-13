import java.io.*;
import java.net.Socket;

public class EchoClient {
    public static void main(String[] args) {
        try{ // 다른 호스트에 접속 요청하는 소켓 생성
        Socket socket = new Socket("127.0.0.1", 8000); // connect 요청
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            //데이터 수신
            String msg = in.readLine(); // 수신, block mode.
            System.out.println(msg);
            for(int i =0; i<1000; i++) {
                out.println("Hi!!!");
                out.flush();
            }
            //접속종료
            //in.close();
            //out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

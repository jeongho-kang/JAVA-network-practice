import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 8008)) { // 서버로 연결요청하고 연결되면 소켓 생성
            // 송수신 채널 생성
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            // 서버에게 데이터 보내기
            for(int i=0; i<10; i++) {
                System.out.println("Send to server : line " + i);
                out.println("line " + i);  // 송신
                out.flush();
            }
            out.println("BYE");
            out.flush();
            // 서버로부터 데이터 받기
            while (true) {
                String line = in.readLine();
                if(line == null) break;
                System.out.println(line);
            }
            in.close();
            out.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

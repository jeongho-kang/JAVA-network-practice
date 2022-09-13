import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) {
        try {
            // ServerSocket 객체 생성
            ServerSocket ss = new ServerSocket(8000);
            System.out.println("Echo Server is waiting for client.");
            Socket client = ss.accept(); // client 접속을 waiting하다가 접속되면 해당 client 와 통신할 socket 을 생성
            // 데이터 송수신을 위한 스트림 객체 생성
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
            System.out.println("Connection is OK : " + client.getInetAddress().getHostAddress() + ":" +
                    client.getPort());
            // 데이터 전송
            out.println("Hello This is Echo Server!"); // 송신버퍼에 담기
            out.flush(); // 송신버퍼에 담긴 데이터를 즉시 송신처리
            //접속종료
            while(true) {
                String str = in.readLine();
                System.out.println("Client`s msg : " + str);
                if(str.equals("BYE") || str == null) break;
            }
            in.close();
            out.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

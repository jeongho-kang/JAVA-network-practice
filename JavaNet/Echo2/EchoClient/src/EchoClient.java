import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class EchoClient {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try { // 서버로 연결요청하고 연결되면 소켓 생성
            Socket socket = new Socket("127.0.0.1", 8008);
            // 송수신 채널 생성
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            // 웰컴메세지 수신하기
            String message = in.readLine();
            System.out.println(message);
            message = in.readLine();
            System.out.println(message);

            // 서버에게 데이터 보내기
            /*
            for(int i=0; i<10; i++) {
                System.out.println("Send to server : line " + i);
                out.println("line " + i);  // 송신
                out.flush();
            }
            out.println("BYE");
            out.flush();*/

            // 서버와의 데이터 송수신
            /*
            while (true) {
                // 서버에 채팅메세지 보내기
                System.out.print("Chat: ");
                message = sc.nextLine();
                out.println(message); out.flush();
                if(message.equals("BYE")) break;
                // 서버로부터 데이터 수신하기
                String line = in.readLine();
                if(line == null) break;
                System.out.println(line);
            }*/
            // 데이터 송신 쓰레드 생성하기
            Thread sendThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        System.out.print("Chat: ");
                        String message = sc.nextLine();
                        out.println(message);
                        out.flush();
                        if (message.equals("BYE")) {
                            try {
                                in.close();
                                out.close();
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                    }
                }
            });
            // 데이터 수신 쓰레드 생성하기
            Thread recvThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        String line;
                        try {
                            line = in.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                            break;
                        }
                        if(line == null) break;
                        System.out.println(line);
                    }
                }
            });
            // 쓰레드 동작하기
            sendThread.start();
            recvThread.start();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

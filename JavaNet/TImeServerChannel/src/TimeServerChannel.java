import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;

import static java.lang.Thread.sleep;

public class TimeServerChannel {
    public static void main(String[] args) {
        System.out.println("TIme server is started");
        try {
            ServerSocketChannel sschannel = ServerSocketChannel.open();
            sschannel.configureBlocking(true); // 서버소켓채널이 블로킹 모드로 동작
            sschannel.socket().bind(new InetSocketAddress(5002)); // 채널과 포트번호를 바인드 시킴
            while (true) {
                System.out.println("Waiting for connection...");
                SocketChannel client = sschannel.accept();
                InetSocketAddress clientAdress = (InetSocketAddress) client.getRemoteAddress();
                System.out.println("Client: "+clientAdress.getAddress()+ " " +clientAdress.getPort());
                if(client != null) { // 연결되었으면
                    ByteBuffer buf = ByteBuffer.allocate(64); // 64byte 크기의 논다이렉트 버퍼 생성
                    for(int i=0; i<10; i++) {
                        String currentTime = "Date: " + new Date(System.currentTimeMillis());
                        buf.clear(); // position과 limit 속성값을 초기화 시킨다. 데이터를 지우는건 아님.
                        // 데이터를 버퍼에 기록하기
                        buf.put(currentTime.getBytes()); // 스트링 데이타를 바이트 데이타로 바꿔서 버퍼로 담음.
                        buf.flip(); // 기록상태 -> 읽기상태 : position = 0, limit = 기록한 데이타크기
                        while (buf.hasRemaining()) { //position < limit 동안
                            client.write(buf); // buf --> client 소켓채널
                        }
                        System.out.println("Sent: " + currentTime);
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    client.close(); // 접속종료
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

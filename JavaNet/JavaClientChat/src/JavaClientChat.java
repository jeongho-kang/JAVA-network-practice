import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class JavaClientChat {
    private static void sendMsg(SocketChannel channel,String msg) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(msg.length() + 1);
            buffer.put(msg.getBytes());
            buffer.put((byte) 0x00); // 메세지의 끝을 가리키는 mark
            buffer.flip(); // 기록 -> 읽기
            while (buffer.hasRemaining()) {

                channel.write(buffer);
                show("Sent: " + msg);
            }
        }       catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static String recvMsg(SocketChannel channel) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            String msg = "";
            while(channel.read(buffer) > 0) {
                char byteRead = 0x00;
                buffer.flip();
                while (buffer.hasRemaining()) { // buffer로부터 글자를 하나씩 읽어옴
                    byteRead = (char) buffer.get();
                    if(byteRead == 0x00) break;
                    msg += byteRead;
                }
                if(byteRead == 0x00) break;
            }
            return msg;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    static class Player {
        String name ="";
        String sayWord() {
            Scanner sc = new Scanner(System.in);
            String word = sc.nextLine();
            return word;
        }
        int sud(String word, String compare) {
            int result=0;

            int lastIndex = compare.length()-1;
            char lastChar = compare.charAt(lastIndex);
            char firstChar = word.charAt(0);

            if(firstChar==lastChar)
                result=1;
            else
                result=0;
            return result;
        }
    }

    private static void show(String msg) {
        System.out.println(msg);
    }
    public static void main(String[] args) {
        SocketAddress address = new InetSocketAddress("127.0.0.1", 3005);
        try {
            SocketChannel socket = SocketChannel.open(address);
            show("게임서버 접속 완료.");
            String message;
            String name;
            Scanner sc = new Scanner(System.in);
            System.out.print("게임에 참가하는 인원은 몇명입니까 >> ");
            int playercount = sc.nextInt();
            Player[] playerList = new Player[playercount];

            for(int i=0; i<playercount; i++) {
                System.out.print("참가자의 이름을 입력하시오 >>");
                playerList[i] = new Player();
                playerList[i].name=sc.next();
            }
            System.out.println("시작하는 단어는 dog 입니다.");
            String FirstWord = "dog";

            int i = 0;
            String compare = FirstWord;
            while(true) {
                System.out.print(playerList[i].name+">>");
                String word = playerList[i].sayWord();
                if(playerList[i].sud(word,compare) ==1) {
                    i++;
                    compare = word;
                    if(i==playercount) i=0;
                    continue;
                } else {
                    System.out.println(playerList[i].name + "이 졌습니다.");
                    System.exit(1);
                }
                message = sc.nextLine();
                sendMsg(socket, message);
                if(message.equals("quit")) break;
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



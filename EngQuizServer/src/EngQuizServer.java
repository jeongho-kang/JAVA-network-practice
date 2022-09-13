import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class EngQuizServer {
    static Vector<EngChatting> ar = new Vector<>();
    static int c = 0;
    private String file;
    private static HashMap<String, String> word;
    private static Scanner sc = new Scanner(System.in);
    public static String[][] question;
    public static String[][] answer;
    public static String[][] noanswer;

    static int quiz_count = 0;
    static int e = 0;
    static int corrects[] = new int[10];

    public EngQuizServer(String file) {
        this.file = file;
        word = new HashMap<>();
    }

    public EngQuizServer() {

    }


    public void addWords() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, ",");
                String english = tokenizer.nextToken();
                String[] tokens = line.split(",", 2);
                String korean[] = tokens;

                word.put(english, korean[1]);
            }
            System.out.println("단어장이 생성되었습니다.");
        } catch (FileNotFoundException e) {
            System.out.println("File OPEN Error : " + file + "을 찾을 수 없습니다.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        EngQuizServer voca = new EngQuizServer("voca1800.txt");
        voca.addWords();
        ServerSocket ss = new ServerSocket(3005);
        Socket s;
            while (true) {
                System.out.print("The number of words in a quiz : ");
                int quantity = sc.nextInt();
                sc.nextLine();
                quiz_count++;
                question = new String[quiz_count][quantity];
                answer = new String[quiz_count][quantity];
                noanswer = new String[quiz_count][quantity];
                for (int i = 0; i < quantity; i++) {
                    Object[] keys = word.keySet().toArray();
                    String randomKey = (String) keys[new Random().nextInt(keys.length)];
                    question[e][i] = randomKey;
                    answer[e][i] = word.get(randomKey);
                }
                while (true) {
                    System.out.println("English Server is waiting.....");
                    s = ss.accept();
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                    System.out.println("New Client is connected : " +
                            "Socket[addr =/" + s.getInetAddress().getHostAddress() + " port=" + s.getPort() +
                            " localport=" + s.getLocalPort());
                    String name = dis.readUTF();
                    System.out.println("Creating a new Quiz Maker for this client : " + name);
                    EngChatting mtch = new EngChatting(s, name, dis, dos);
                    Thread t = new Thread(mtch);
                    System.out.println("Adding this client to active client list");
                    ar.add(mtch);
                    t.start();
                    c++;
                }
            }
    }
}









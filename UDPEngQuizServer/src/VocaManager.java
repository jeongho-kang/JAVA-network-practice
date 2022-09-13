import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class VocaManager {
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

    public VocaManager(String file) {
        this.file = file;
        word = new HashMap<>();
    }

    public VocaManager() {

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

    public void create_quiz() {
        quiz_count++;
        System.out.print("생성할 퀴즈 갯수 : ");
        int quantity = sc.nextInt();
        sc.nextLine();
        question = new String[quiz_count][quantity];
        answer = new String[quiz_count][quantity];
        noanswer = new String[quiz_count][quantity];
        int j = 0, correct = 0;
        for (int i = 0; i < quantity; i++) {
            Object[] keys = word.keySet().toArray();
            String randomKey = (String) keys[new Random().nextInt(keys.length)];
            question[e][i] = randomKey;
            answer[e][i] = word.get(randomKey);
            System.out.println((i + 1) + "번 문제 : " +
                    question[e][i] + " // " + answer[e][i]);


        }
    }
}

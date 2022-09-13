import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class VocaHash {
    private String file;
    private HashMap<String, String> map;

    public String[][] question;
    public String[][] answer;
    public String[][] wrongword;

    int quiz_count = 0;
    int e=0;
    int corrects[] = new int[10];
    int quizhistory_question[] = new int[10];

    Scanner sc = new Scanner(System.in);

    public VocaHash(String file){
        this.file = file;
        map = new HashMap<>();
    }
    public void make_voca() {
        try {
            BufferedReader buf = new BufferedReader(new FileReader(file));
            String line;
            while ((line = buf.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, ",");
                String english = tokenizer.nextToken();
                String[] tokens = line.split(",", 2);
                String korean[] = tokens;

                map.put(english, korean[1]);
            }
            System.out.println("단어장이 생성되었습니다.");
        } catch (FileNotFoundException e) {
            System.out.println("File OPEN Error : " + file + "을 찾을 수 없습니다.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 /*   public void show(){
        for(Map.Entry entry : map.entrySet()) System.out.println(entry.getKey() + " " + entry.getValue());
    }*/
    public void sort() {
        List<Map.Entry<String, String>> entries = map.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toList());
        for (Map.Entry<String, String> entry : entries) {
            System.out.println(entry.getKey() + " "  + entry.getValue());
        }
    }

    public void search() {
        System.out.print("검색할 단어를 입력해주세요 : ");
        String word = sc.next();
        String value = (String) map.get(word);
        if(value != null) System.out.println("검색한 단어의 뜻 : " + value);
        else System.out.println("입력하신 단어가 단어장에 존재하지 않습니다.");
    }

    public void add() {
        System.out.println("추가할 단어를 입력해주세요.");
        System.out.print("영어 : ");
        String english = sc.next();
        System.out.print("한글 : ");
        String korean = sc.next();
        map.put(english,korean);
        System.out.println("입력하신 단어가 단어장에 추가됬습니다.");
    }

    public void remove() {
        System.out.print("단어장에서 삭제 할 단어를 입력해주세요 : ");
        String word = sc.next();
        map.remove(word);
        System.out.println("입력하신 단어가 단어장에서 삭제됬습니다.");
    }

    public void quiz() {
        quiz_count++;
        System.out.print("생성할 퀴즈 갯수 : ");
        int quantity = sc.nextInt();
        sc.nextLine();
        question = new String[quiz_count][quantity];
        answer = new String[quiz_count][quantity];
        wrongword = new String[quiz_count][quantity];
        String answerword;
        int j = 0,correct=0;
        for(int i=0; i<quantity; i++) {

            Object[] keys = map.keySet().toArray();
            String randomKey = (String) keys[new Random().nextInt(keys.length)];
            
            question[e][i] = randomKey;
            answer[e][i] = map.get(randomKey);

            System.out.print((i+1) + "번 문제 : " + question[e][i] + " // 답 : ");
            answerword = sc.nextLine();


            if(answer[e][i].equals(answerword)) correct++;
            else wrongword[e][j++] = question[e][i];
        }
        quizhistory_question[e]=question.length;
        corrects[e]=correct;
        System.out.println("맞힌 갯수 : " + correct);
        System.out.print("틀린 단어 : ");
        for(int i=0; i< wrongword[e].length; i++) {
            if(wrongword[e][i] != null) System.out.print(wrongword[e][i] + " ");
        }
        e++;
        System.out.println();

    }


    public void quiz_history(){
            for(int i=0; i<quiz_count; i++) {
                System.out.println((i+1) + "회차 퀴즈 갯수 : " + quizhistory_question[i]);
                System.out.println((i+1) + "회차 정답 수 : " + corrects[i]);
                /*System.out.print((i+1) + "회차 오답 단어 : " );
                for(int k=0; k<wrongword[i].length; k++) {
                    if(wrongword[i][k] != null)System.out.print(wrongword[i][k] + " ");
                }*/
                System.out.println();
            }
    }
}

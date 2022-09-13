import java.util.Scanner;

public class VocaMain {
    public static void main(String[] args) {
        int n=10;
        Scanner sc = new Scanner(System.in);
        VocaHash voca = new VocaHash("voca1800.txt");
        System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
        System.out.println("        Voca 1800 단어장 프로그램          ");
        System.out.println("");
        System.out.println("      1.Voca 1800 단어집 생성하기");
        System.out.println("      2.단어 알파벳 순으로 정렬 후 출력");
        System.out.println("      3.특정 영단어 검색하기");
        System.out.println("      4.영어 단어 추가하기");
        System.out.println("      5.영어 단어 삭제하기");
        System.out.println("      6.영어 암기 퀴즈 생성하기");
        System.out.println("      7.퀴즈 결과 확인하기");
        System.out.println("      0.단어장 종료");
        System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");




        while (n != 0) {
            System.out.print("메뉴 선택 : ");
            n = sc.nextInt();
            switch (n) {
                case 1: voca.make_voca(); break;
                case 2: voca.sort(); break;
                case 3: voca.search(); break;
                case 4: voca.add(); break;
                case 5: voca.remove(); break;
                case 6: voca.quiz(); break;
                case 7: voca.quiz_history(); break;
            }
        }
    }
}

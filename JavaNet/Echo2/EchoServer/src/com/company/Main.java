package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //try (ServerSocket ss = new ServerSocket(8008)) {
        try {
            ServerSocket ss = new ServerSocket(8008);  // 서버소켓을 8008포트에 생성
            while(true) {
                System.out.println("Waiting for connection...");
                Socket cs = ss.accept();  // 서버소켓이 클라이언트의 접속을 기다리다가 연결되면 클라이언트 소켓을 생성
                System.out.println("Connection : " + cs.getInetAddress().getHostAddress());
                // 송수신 채널 생성
                BufferedReader in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(cs.getOutputStream()));
                // 접속한 클라이언트에게 웰컴 메세지 전송
                out.println("Welcome! This is a EchoServer.");
                out.println("Enter BYE to exit.");
                out.flush();  // 버퍼를 비우고 즉시 전송하기
                // 송수신 처리
                while (true) {
                    String line = in.readLine();  // 수신
                    System.out.println("Received: " + line);
                    if (line == null) break;   // 상대방이 연결을 종료했을 때
                    System.out.print("Chat: ");
                    line = sc.nextLine();
                    out.println("Echo : " + line);  // 송신
                    out.flush();
                    if (line.equals("BYE")) break;
                }
                // 접속종료
                in.close();
                out.close();
                cs.close();
                // 서버 종료 확인
                char yn;
                System.out.print("Quit server?(y/n) ");
                yn = sc.next().charAt(0);  // 문자 하나 입력
                if(yn == 'y' || yn == 'Y') {
                    System.out.println("EchoServer is closed.");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

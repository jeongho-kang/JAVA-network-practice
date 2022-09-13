import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.nio.Buffer;

public class SSLClient {
    public static void main(String[] args) throws IOException {
        String host = "www.oracle.com";
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault(); // 오브젝트 생성
        SSLSocket socket = (SSLSocket) factory.createSocket(host, 443);
        socket.startHandshake(); // SSL handshake
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
        out.println("GET http://" + host+"/index.html HTTP/1.1");
        out.println();
        out.flush();

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) System.out.println(line);

        in.close();
        out.close();
        socket.close();
    }
}

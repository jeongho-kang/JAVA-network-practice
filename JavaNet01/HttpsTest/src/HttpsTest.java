import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.Certificate;

public class HttpsTest {
    public static void getUrlInfo() throws IOException {
        URL url = new URL("https:www.oracle.com");
        System.out.println("URL Infomation");
        System.out.println("    URL Class Name: " + url.getClass().getName());
        System.out.println("    URL as String: " + url.toString());
        System.out.println("    URL Protocol: " + url.getProtocol());
    }
    public static void getHtml() throws IOException {
        URL url = new URL("https:www.oracle.com");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String line;
        while((line = reader.readLine()) != null) System.out.println(line);
    }
    public static void getCertificate() throws IOException{
        URL url = new URL("https:www.oracle.com");
        URLConnection con = url.openConnection();
        System.out.println("Connection Class Name: " + url.getClass().getName());
        HttpsURLConnection scon = (HttpsURLConnection) con;
        scon.connect();
        Certificate[] certs = scon.getServerCertificates();
        System.out.println("Server Certificate: " + certs[0].toString());
    }

    public static void main(String[] args) throws IOException {
        //getUrlInfo();
        //getHtml();
        getCertificate();
    }
}

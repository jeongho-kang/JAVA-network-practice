import java.net.*;
import java.util.Collections;
import java.util.Enumeration;

public class NetInterface {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            Enumeration<NetworkInterface> netEnum = NetworkInterface.getNetworkInterfaces();
            System.out.printf("Name     Display name\n");
            for(NetworkInterface element : Collections.list(netEnum)) {
                System.out.printf("%-8s %-32s\n", element.getName(), element.getDisplayName());
            }
            // MAC address
            InetAddress address = null;
            try {
                address = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("IP address: " + address.getHostAddress());
            NetworkInterface network = NetworkInterface.getByInetAddress(address);
            System.out.println("MAC address: " + getMACaddress(network));
            // URI address
            try {
                URI uri = new URI("https://comic.naver.com/index.nhn");
                displayURI(uri);
            } catch (URISyntaxException ex) {}
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static String getMACaddress(NetworkInterface netCard) {
        StringBuilder address = new StringBuilder();
        try {
            byte[] macBuffer = netCard.getHardwareAddress();
            if(macBuffer != null) {
                for(int i=0; i<macBuffer.length; i++) {
                    address.append(String.format("%02X%s",macBuffer[i],
                            (i < macBuffer.length - 1) ? "-" : ""));
                }
            }
            else return "Can't find MAC address";
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return address.toString();
    }
    private static void displayURI(URI uri) {
        System.out.println("getAuthority: " + uri.getAuthority());
        System.out.println("getScheme: " + uri.getScheme());
        System.out.println("getSchemeSpecificPart: "
                + uri.getSchemeSpecificPart());
        System.out.println("getHost: " + uri.getHost());
        System.out.println("getPath: " + uri.getPath());
        System.out.println("getQuery: " + uri.getQuery());
        System.out.println("getFragment: " + uri.getFragment());
        System.out.println("getUserInfo: " + uri.getUserInfo());
        System.out.println("normalize: " + uri.normalize());
    }
}

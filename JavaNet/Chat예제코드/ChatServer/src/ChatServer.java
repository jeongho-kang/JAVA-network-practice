import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ChatServer {
    // Vector to store active clients
    static Vector<ClientHandler> ar = new Vector<>();
    // counter for clients
    static int i = 0;
    public static void main(String[] args) throws IOException
    {
        // server is listening on port 1234
        ServerSocket ss = new ServerSocket(3005);
        Socket s;
        // running infinite loop for getting
        // client request
        while (true)
        {
            // Accept the incoming request
            System.out.println("Chat server is waiting...");
            s = ss.accept();
            System.out.println("New client request received : " + s);
            // input and output streams
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            System.out.println("New Client is connected : " +
                    "Socket[addr =/" + s.getInetAddress().getHostAddress() + " port=" + s.getPort() +
                    " localport="+ s.getLocalPort());
            String name = dis.readUTF();
            System.out.println("Creating a new handler for this client : " + name);
            // Create a new handler object for handling this request.
            ClientHandler mtch = new ClientHandler(s, name, dis, dos);
            // Create a new Thread with this object.
            Thread t = new Thread(mtch);
            System.out.println("Adding this client to active client list");
            // add this client to active clients list
            ar.add(mtch);
            // start the thread.
            t.start();
            // increment i for new client.
            // i is used for naming only, and can be replaced
            // by any naming scheme
            i++;
        }
    }
}

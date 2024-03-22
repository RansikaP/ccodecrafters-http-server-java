import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        // You can use print statements as follows for debugging, they'll be visible when running tests.
        System.out.println("Logs from your program will appear here!");

        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            Server http = new Server(4221);
            System.out.println("accepted new connection");
            http.start();
        } catch (IOException e) {
            System.out.println("Could not Create Server: " + e.getMessage());
        }
    }
}

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;

public class Server {
    private int port;
    private ServerSocket serverSocket;
    private Socket clientSocket = null;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(4221);
        serverSocket.setReuseAddress(true);
        clientSocket = serverSocket.accept();
    }

    public void start() {
        try {
            clientSocket.getOutputStream().write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
            clientSocket.getOutputStream().flush();
        } catch (IOException e) {
            System.out.println("Could not start server: " + e.getMessage());
        }
    }
}

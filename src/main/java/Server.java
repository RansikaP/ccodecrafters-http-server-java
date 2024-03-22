import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
            ServerHandler handler = new ServerHandler(clientSocket.getInputStream(), clientSocket.getOutputStream());
            handler.run();
        } catch (IOException e) {
            System.out.println("Could not start server: " + e.getMessage());
        }

    }
}

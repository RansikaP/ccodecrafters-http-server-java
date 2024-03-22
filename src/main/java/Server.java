import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        ExecutorService threads = Executors.newCachedThreadPool();

        try {
            while (true) {
                clientSocket = serverSocket.accept();
                threads.submit(new ServerHandler(clientSocket.getInputStream(), clientSocket.getOutputStream()));
            }

        } catch (IOException e) {
            System.out.println("Could not start server: " + e.getMessage());
        }

    }
}

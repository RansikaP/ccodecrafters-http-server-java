import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
            HttpParser parser = new HttpParser(clientSocket.getInputStream());
            parser.parseRequest();

            if (parser.getRequestURL().equals("/"))
                clientSocket.getOutputStream().write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
            else if (parser.getRequestURL().contains("/echo")) {
                clientSocket.getOutputStream().write("HTTP/1.1 200 OK\r\n".getBytes());
                clientSocket.getOutputStream().write("Content-Type: text/plain\r\n".getBytes());
                clientSocket.getOutputStream().write(String.format("Content-Length: %d\r\n\r\n", parser.getRequestURL().substring(6).length()).getBytes());
                clientSocket.getOutputStream().write(parser.getRequestURL().substring(6).getBytes());
            }
            else
                clientSocket.getOutputStream().write("HTTP/1.1 404 Not Found\r\n\r\n".getBytes());
            clientSocket.getOutputStream().flush();
        } catch (IOException e) {
            System.out.println("Could not start server: " + e.getMessage());
        }
    }
}

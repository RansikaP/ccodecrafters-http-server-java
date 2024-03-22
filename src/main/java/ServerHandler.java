import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ServerHandler implements Runnable {
    private InputStream input;
    private OutputStream output;
    private Server server;

    public ServerHandler(InputStream input, OutputStream output, Server server) {
        this.input = input;
        this.output = output;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            HttpParser parser = new HttpParser(input);
            parser.parseRequest();

            if (parser.getRequestURL().equals("/"))
                this.httpResponseOK();
            else if (parser.getRequestURL().startsWith("/echo")) {
                String body = parser.getRequestURL().substring(6);
                this.httpResponseText(body);
            } else if (parser.getRequestURL().startsWith("/user-agent")) {
                String body = parser.getHeader("User-Agent");
                this.httpResponseText(body);
            } else if (parser.getRequestURL().startsWith("/files")) {
                String file = parser.getRequestURL().substring(7);
                System.out.println(fileExists(file));
            }
            else
                this.httpResponseNotFound();
        } catch (IOException e) {
            System.out.println("Could not start handler: " + e.getMessage());
        }

    }

    private void httpResponseOK() throws IOException {
        output.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
        output.flush();
    }

    private void httpResponseNotFound() throws IOException {
        output.write("HTTP/1.1 404 Not Found\r\n\r\n".getBytes());
        output.flush();
    }

    private void httpResponseText(String body) throws IOException {
        output.write("HTTP/1.1 200 OK\r\n".getBytes());
        output.write("Content-Type: text/plain\r\n".getBytes());
        output.write(String.format("Content-Length: %d\r\n\r\n", body.length()).getBytes());
        output.write(body.getBytes());
        output.flush();
    }

    private boolean fileExists(String file) {
        return new File(this.server.getDirectory(), file).exists();
    }
}

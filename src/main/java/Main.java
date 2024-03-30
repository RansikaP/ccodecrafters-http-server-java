import java.io.IOException;
import org.apache.commons.cli.*;

public class Main {
    public static void main(String[] args) {
        // You can use print statements as follows for debugging, they'll be visible when running tests.
        System.out.println("Logs from your program will appear here!");
        Server http = null;
        Options options = new Options();
        CommandLineParser cmdParser = new DefaultParser();

        Option directoryOption = Option.builder().longOpt("directory").hasArg().desc("Server Directory").build();
        options.addOption(directoryOption);

        try {
            CommandLine cmd = cmdParser.parse(options, args);
            if (cmd.hasOption("directory"))
                http = new Server(4221, cmd.getOptionValue("directory"));
            else
                http = new Server(4221, null);
            http.start();
        } catch (IOException | ParseException e) {
            System.out.println("Could not Create Server: " + e.getMessage());
        }
    }
}

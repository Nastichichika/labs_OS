import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Server server = new Server("localhost", 2809, 2);
        server.start();
    }
}

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.List;

public class Server {
    private ServerSocketChannel serverSocketChannel;
    int number;
    boolean working = true;
    List<Process> clientProcesses;

    public Server(int number){
        this.number = number;
    }

    public void start() throws IOException {
        openSocketServer();

        runClients();

        while(working){
            SocketChannel socketChannel = serverSocketChannel.accept();

            //reading

            socketChannel.close();
        }
    }
    void openSocketServer() throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress("localhost", 2809));
        serverSocketChannel.configureBlocking(false);
    }

    private void startProcess(String s) throws InterruptedException, IOException {
        ProcessBuilder builder = new ProcessBuilder("java", "-jar", "C:", String.valueOf(this.number));
        Process process = builder.start();
        clientProcesses.add(process);
    }

    void runClients() throws IOException {

    }

    String read(SocketChannel socketChannel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        socketChannel.read(buffer);
        String result = new String(buffer.array()).trim();
        return result;
    }
}
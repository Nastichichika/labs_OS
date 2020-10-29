import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.*;

public class Server {
    private ServerSocketChannel serverSocketChannel;
    int number;
    boolean working = true;
    ArrayList<Process> clientProcesses = new ArrayList<Process>();

    ArrayList<String> result = new ArrayList<String>();
    public Server(int number){
        this.number = number;
    }

    public void start() throws IOException, InterruptedException {
        openSocketServer();

        runClients();

        while(result.size() < 4){
            SocketChannel socketChannel = serverSocketChannel.accept();
            if(socketChannel != null) {
                try {
                    read(socketChannel);
                    if(result.get(1).equals("0")) {
                        if(result.get(0).equals("F"))
                            result.add("G");
                        else
                            result.add("F");
                    }
                    result.add("")
                } catch (IOException e) {
                    this.close();
                }
            }
        }
        multiplication();
    }
    void openSocketServer() throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress("localhost", 2809));
        serverSocketChannel.configureBlocking(false);
    }

    void runClients() throws IOException, InterruptedException {
        if(this.number % 2 == 0) {
            startProcess("gx");
            startProcess("fx");
        }
        else {
            startProcess("fx");
            startProcess("gx");
        }
    }
    private void startProcess(String s) throws InterruptedException, IOException {
        ProcessBuilder builder = new ProcessBuilder("java", "-jar", "F:\\knu\\oop\\untitled\\labs_OS\\lab1\\out\\artifacts\\"+ s + "\\lab1.jar", String.valueOf(this.number));
        Process process = builder.start();
        clientProcesses.add(process);
    }
    private void read(SocketChannel socket) throws IOException {
        ByteBuffer readBuffer = ByteBuffer.allocate(48);
        int bufferSize = socket.read(readBuffer);
        if (bufferSize == -1) {
            socket.close();
        } else {
            readBuffer.flip();
        }

        String returnString = new String(deconstructMessage(readBuffer.array()), "UTF-8").trim();
        result.add(returnString.substring(0,1));
        result.add(returnString.substring(1));
    }
    public byte[] deconstructMessage(byte[] data) {

        return Arrays.copyOfRange(data, 0, data.length );
    }
    public void close() throws IOException {
        for (Process proc : clientProcesses) {
            proc.destroy();
        }
        serverSocketChannel.close();
    }
    void multiplication(){
        for(int i = 0; i < 3; i+=2)
            System.out.println(result.get(i) + "(x) = " + result.get(i+1));
        int res = Integer.parseInt(result.get(1)) * Integer.parseInt(result.get(3));
        System.out.println((res));
    }
}
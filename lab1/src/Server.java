import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.*;

public class Server {
    private ServerSocketChannel serverSocketChannel;
    private int number;
    private String multiplication;
    int working = 2;
    private ArrayList<Process> clientProcesses = new ArrayList<Process>();

    ArrayList<String> result = new ArrayList<String>();
    public Server(int number){
        this.number = number;
    }

    public void start() throws IOException, InterruptedException {
        openSocketServer();

        runClients();

        while(working > 0){
            SocketChannel socketChannel = serverSocketChannel.accept();
            if(socketChannel != null) {
                try {
                    read(socketChannel);
                    if(result.get(1).equals("0")) {
                        --working;
                        cust_show();
                    }

                } catch (IOException e) {
                    this.close();
                }
                socketChannel.close();
            }

        }
        multiplication();
    }
    private void openSocketServer() throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress("localhost", 2809));
        serverSocketChannel.configureBlocking(false);
    }

    private void runClients() throws IOException, InterruptedException {
        if(this.number % 2 != 0) {
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
        System.out.println(returnString.substring(0,1) + "(x) = " + returnString.substring(1));
        result.add(returnString.substring(0,1));
        result.add(returnString.substring(1));
        --working;

    }
    private byte[] deconstructMessage(byte[] data) {
        return Arrays.copyOfRange(data, 0, data.length );
    }
    public void close() throws IOException {
        for (Process proc : clientProcesses) {
            proc.destroy();
        }
        serverSocketChannel.close();
    }
    private void multiplication() throws IOException {
        if(result.size() <= 2) {
            this.multiplication = "undefined";
            return;
        }
        if(result.get(1).equals("0")) {
            this.multiplication = "0";
            return;
        }
        int res = Integer.parseInt(result.get(1)) * Integer.parseInt(result.get(3));
        this.multiplication = String.valueOf(res);
    }
    private void cust_show() {
        if(result.get(0).equals("F"))
            result.add("G");
        else  
            result.add("F");
        result.add("is not computed");

        System.out.println(result.get(2) + "(x) " + result.get(3));
    }
    public void showResult() throws IOException {
        multiplication();
        if(result.size() == 0) {
            System.out.println("F(x) = is not computed");
            System.out.println("G(x) = is not computed");
        }
        else {
            System.out.println(result.get(0) + "(x) = " + result.get(1));
            cust_show();
        }
        showMultiplication();
    }
    public void showMultiplication() {
        System.out.println("Result multiplication is " + this.multiplication);
    }
}
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

    public Server(int number){
        this.number = number;
    }

    public void start() throws IOException, InterruptedException {
        openSocketServer();

        runClients();
        ArrayList<String> result = new ArrayList<String>();
        int i = 0;
        while(i < 2){
            SocketChannel socketChannel = serverSocketChannel.accept();
            if(socketChannel != null) {

                result.add(read(socketChannel));

                socketChannel.close();
            }

            i++;
        }
    }
    void openSocketServer() throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress("localhost", 2809));
        serverSocketChannel.configureBlocking(false);
    }

    void runClients() throws IOException, InterruptedException {
        //startProcess("fx");
        //startProcess("fx");
    }
    private void startProcess(String s) throws InterruptedException, IOException {
        ProcessBuilder builder = new ProcessBuilder("java", "-jar", "F:\\knu\\oop\\untitled\\labs_OS\\lab1\\out\\artifacts\\"+ s + "\\lab1.jar", String.valueOf(this.number));
        Process process = builder.start();
        clientProcesses.add(process);
    }
    String read(SocketChannel socketChannel) throws IOException {
        /*ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.flip();
        int numRead = socketChannel.read(byteBuffer);
        if (numRead == -1) {
            socketChannel.close();
            return "";
        }
        //creating byte array for message
        byte[] data = new byte[numRead];
        System.arraycopy(byteBuffer.array(), 0, data, 0, numRead);
        String gotData = new String(data);
        System.out.println("Got:" + gotData);
        //String result = new String(buffer.array()).trim();
        //System.out.println(result);
        // byteBuffer = ByteBuffer.allocate(1024);


        */
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.flip();

        int numRead = socketChannel.read(byteBuffer);
        if (numRead == -1) {
            socketChannel.close();
            return "";
        }
        //creating byte array for message
        byte[] data = new byte[numRead];
        System.arraycopy(byteBuffer.array(), 0, data, 0, numRead);
        String gotData = new String(data);
        System.out.println("Got:" + gotData);
        return "r";
    }
    private void sendMessage(SocketChannel socket){
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024*1024);
        byteBuffer.put(Integer.toString(number).getBytes());
        byteBuffer.flip();
    }
}
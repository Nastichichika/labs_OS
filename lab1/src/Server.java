import clients.ClientFx;
import clients.ClientGx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.List;

public class Server {
    ServerSocketChannel serverSocketChannel;
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

            write(socketChannel);

            //reading

            socketChannel.close();
        }
    }
    void openSocketServer() throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress("localhost", 2809));
        serverSocketChannel.configureBlocking(false);
    }
    void write(SocketChannel socketChannel) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(Integer.toString(number).getBytes());
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
    }
    void runClients() throws IOException {
        ClientFx fx = new ClientFx();
        ClientGx gx = new ClientGx();
        fx.run();
        gx.run();
    }
}
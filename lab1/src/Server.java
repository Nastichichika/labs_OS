import clients.ClientFx;
import clients.ClientGx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.List;

public class Server {
    InetSocketAddress address = new InetSocketAddress("localhost", 2809);
    int number;
    boolean working = true;
    List<Process> clientProcesses;

    public Server( int number){
        this.number = number;
    }

    public void start() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(address);
        serverSocketChannel.configureBlocking(false);

        ClientFx fx = new ClientFx();
        ClientGx gx = new ClientGx();
        fx.run();
        gx.run();
        //run clients

        while(working){
            SocketChannel socketChannel = serverSocketChannel.accept();

            System.out.println("Server started :)");

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.put(Integer.toString(number).getBytes());
            byteBuffer.flip();

            socketChannel.write(byteBuffer);
            //writing to client

            //reading

            socketChannel.close();
        }
    }
}
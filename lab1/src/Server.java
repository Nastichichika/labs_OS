import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class Server {
    InetSocketAddress address;
    int number;

    public Server(String host, int port, int number){
        this.address = new InetSocketAddress(host, port);
        this.number = number;
    }

    public void start() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9999));
        serverSocketChannel.configureBlocking(false);

        while(true){

            SocketChannel socketChannel = serverSocketChannel.accept();

            System.out.println("Server started :)");

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024*1024);
            byteBuffer.put(Integer.toString(number).getBytes());
            byteBuffer.flip();

            socketChannel.write(byteBuffer);
            //writing to client
            //do something with socketChannel...
        }
    }


}
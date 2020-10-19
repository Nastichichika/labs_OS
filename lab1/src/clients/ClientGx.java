package clients;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class ClientGx {
    SocketChannel socketChannel;
    public ClientGx() throws IOException {
        InetSocketAddress address = new InetSocketAddress("localhost", 2809);
        this.socketChannel = SocketChannel.open(address);
    }

    public void run() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int bytesRead;
        try {
            bytesRead = socketChannel.read(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byteBuffer.flip();

        if(byteBuffer.remaining() == 0){
            byteBuffer.clear();
            return;
        }
    }
}

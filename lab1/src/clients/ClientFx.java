package clients;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class ClientFx extends Thread{
    public ClientFx() throws IOException {
        InetSocketAddress address = new InetSocketAddress("host", 2809);
        SocketChannel socketChannel = SocketChannel.open(address);
    }
}

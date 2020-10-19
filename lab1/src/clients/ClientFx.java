package clients;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class ClientFx{

    SocketChannel socketChannel;

    public ClientFx() throws IOException {
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
    int Fx(int number) throws InterruptedException {
        switch (number) {
            case 1:
                return 28;
            case 2:
                Thread.sleep(2000);
                return 42;
            case 3:
                return 0;
            case 4;
        }
        return number;
    }
}

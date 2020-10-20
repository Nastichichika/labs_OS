package clients.fx;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import spos.lab1.demo.IntOps;
public class ClientFx{

    SocketChannel socketChannel;
    int number;


    public ClientFx(int number) throws IOException {
        this.socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 2809));
        this.number = this.number;
    }

    private int Fx(int number) throws InterruptedException {
        switch (number) {
            case 1:
                return 28;
            case 2:
                Thread.sleep(2000);
                return 42;
            case 3:
                return 0;
            case 4:
            case 5:
                return 9;
            case 6:
            default:
                throw new IllegalArgumentException("Error");
        }
    }

    public void run() throws InterruptedException, IOException {
        int temp = Fx(this.number);
        int temp1 = IntOps.funcF(this.number);
        this.number = temp;
        write();

    }

    void write() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        String str = String.valueOf(number) + " fx";
        buffer.put(str.getBytes());
        buffer.flip();
        socketChannel.write(buffer);
        buffer.clear();
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        int number =  Integer.parseInt(args[0]);
        ClientFx clientFx = new ClientFx(number);
    }
}

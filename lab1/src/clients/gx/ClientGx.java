package clients.gx;

import spos.lab1.demo.IntOps;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class ClientGx {
    private SocketChannel socketChannel;
    private int number;

    public ClientGx(int number) throws IOException {
        this.socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 2809));
        this.socketChannel.configureBlocking(false);
        this.number = number;
    }

    private int Gx(int number) throws InterruptedException {
        switch (number) {
            case 1: {
                Thread.sleep(3000);
                return 42;
            }
            case 2: {
                return 28;
            }
            case 3: {
                while(true) {
                    int i = 1;
                }
            }
            case 4: {
                return 0;
            }
            case 5: {
                while(true) {
                    int i = 1;
                }
            }
            case 6: {
                return 9;
            }
            default:
                throw new IllegalArgumentException("Error");
        }
    }

    void run() throws IOException, InterruptedException {
        int temp = Gx(this.number);
        //int temp1 = IntOps.funcG(this.number);
        this.number = temp;
        this.write();
    }

    private void write() throws IOException {
        String str = "G" + String.valueOf(this.number);

        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.clear();
        buf.put(str.getBytes());

        buf.flip();

        while(buf.hasRemaining()) {
            socketChannel.write(buf);
        }
        socketChannel.close();
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        int number =  Integer.parseInt(args[0]);
        ClientGx clientGx = new ClientGx(number);
        clientGx.run();
    }
}

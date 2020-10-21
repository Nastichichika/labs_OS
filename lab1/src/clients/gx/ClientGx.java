package clients.gx;

import spos.lab1.demo.IntOps;

import java.awt.event.KeyListener;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class ClientGx {
    SocketChannel socketChannel;
    int number;


    public ClientGx(int number) throws IOException {
        this.socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 2809));
        this.number = this.number;
    }

    int Gx(int number) throws InterruptedException {
        switch (number) {
            case 1:
                Thread.sleep(2000);
                return 42;
            case 2:
                return 28;
            case 3:
            case 4:
                return 0;
            case 5:
            case 6:
                return 9;
            default:
                throw new IllegalArgumentException("Error");
        }
    }

    public void run() throws IOException, InterruptedException {
        int temp = Gx(this.number);
        int temp1 = IntOps.funcG(this.number);
        this.number = temp;
        write();
    }

    void write() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        String str = String.valueOf(number) + " gx";
        buffer.put(str.getBytes());
        buffer.flip();
        socketChannel.write(buffer);
        buffer.clear();
    }
    public class KeyEvent implements KeyListener {

        @Override
        public void keyTyped(java.awt.event.KeyEvent e) {

        }

        @Override
        public void keyPressed(java.awt.event.KeyEvent e) {

        }

        @Override
        public void keyReleased(java.awt.event.KeyEvent e) {

        }
    }
    public static void main(String[] args) throws InterruptedException, IOException {
        int number =  Integer.parseInt(args[0]);
        ClientGx clientFx = new ClientGx(number);
    }
}

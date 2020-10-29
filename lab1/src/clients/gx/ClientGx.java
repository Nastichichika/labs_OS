package clients.gx;

import clients.fx.ClientFx;
import spos.lab1.demo.IntOps;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
        this.socketChannel.configureBlocking(false);
        this.number = number;
    }

    int Gx(int number) throws InterruptedException {
        switch (number) {
            case 1: {
                Thread.sleep(5000);
                return 42;
            }
            case 2: {
                return 28;
            }
            case 3:
            case 4: {

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

    public void run() throws IOException, InterruptedException {
        int temp = Gx(this.number);
        //int temp1 = IntOps.funcG(this.number);
        this.number = temp;
        this.write();
    }

    void write() throws IOException {
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

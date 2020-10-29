package clients.fx;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;


import spos.lab1.demo.IntOps;

//import javax.swing.*;

public class ClientFx{

    SocketChannel socketChannel;
    int number;

    public ClientFx(int number) throws IOException {
        this.socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 2809));
        this.socketChannel.configureBlocking(false);
        this.number = number;
    }

    private int Fx(int number) throws InterruptedException {
        switch (number) {
            case 1: {
                return 28;
            }
            case 2: {
                Thread.sleep(5000);
                return 42;
            }
            case 3: {
                return 0;
            }
            case 4:
            case 5: {
                return 9;
            }
            case 6: {
                while(true) {
                    int i = 1;
                }
            }
            default:
                throw new IllegalArgumentException("Error");
        }
    }

    public void run() throws InterruptedException, IOException {
        int temp = Fx(this.number);
        //int temp1 = IntOps.funcF(this.number);
        this.number = temp;
        this.write();
    }

    void write() throws IOException {

        String str = "F" + String.valueOf(this.number);

        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.clear();
        buf.put(str.getBytes());

        buf.flip();

        while(buf.hasRemaining()) {
            socketChannel.write(buf);
        }
        socketChannel.close();
    }
   /* void stop() throws InterruptedException {
        JFrame jf = new JFrame();

        jf.setTitle("Do you want to stop?");
        jf.setSize(200, 100);
        JButton cont = new JButton("Continue");
        JButton st = new JButton("Stop");
        JPanel panel = new JPanel();

        panel.add(cont);
        panel.add(st);

        jf.getContentPane().add(panel);

        jf.setVisible(true);
        jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
        final boolean[] a = {false};
        cont.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                a[0] = true;
                return;
            }
        });
        st.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(-1);
            }
        });
        if(a[0]) return;
        Thread.sleep(15000);
        System.exit(-1);

    }*/
    public static void main(String[] args) throws InterruptedException, IOException {
        int number =  Integer.parseInt(args[0]);
        ClientFx clientFx = new ClientFx(number);
        clientFx.run();
    }
}

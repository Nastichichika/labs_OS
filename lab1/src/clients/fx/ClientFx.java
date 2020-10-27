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

import javax.swing.*;

public class ClientFx{

    SocketChannel socketChannel;
    int number;
    String res;

    public ClientFx(int number) throws IOException {
        this.socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 2809));
        this.number = number;
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
        this.write();
    }



    void write() throws IOException {
        //tring str = "F" + String.valueOf(this.number);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        String str = String.valueOf(this.number) +  " f(x)";
        buffer.put(str.getBytes());
        buffer.flip();
        socketChannel.write(buffer);
        buffer.clear();
    }

    public class KeyEvent implements KeyListener {

        @Override
        public void keyTyped(java.awt.event.KeyEvent e) {
            if((e.isControlDown() && e.getID() == 'c') || e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE
                    || e.getKeyCode() == 'q') {
                try {
                    stop();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        }

        @Override
        public void keyPressed(java.awt.event.KeyEvent e) {

        }

        @Override
        public void keyReleased(java.awt.event.KeyEvent e) {

        }
    }
    void stop() throws InterruptedException {
        JFrame jf = new JFrame();

        jf.setTitle("Do you want to stop?");
        jf.setSize(400, 400);

        JButton cont = new JButton("Continue"+ this.number);
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

    }
    public static void main(String[] args) throws InterruptedException, IOException {
        int number =  Integer.parseInt(args[0]);
        ClientFx clientFx = new ClientFx(number);

        clientFx.stop();
        clientFx.run();
        clientFx.stop();
    }
}

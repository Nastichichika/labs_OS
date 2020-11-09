public class Bakery extends Thread {

    public static final int numberOfThreads = 5;


    private static boolean[] choosing = new boolean[numberOfThreads];

    private static int[] ticket = new int[numberOfThreads];

    public Bakery() {
    }

    public void run() {

    }

    public void lock(int i) {
        choosing[i] = true;
        ticket[i] = findMax() + 1;
        choosing[i] = false;
    }

    private void unlock(int i) {
        ticket[i] = 0;
    }
    private int findMax() {
        int m = ticket[0];
        for (int i = 1; i < ticket.length; i++) {
            if (ticket[i] > m)
                m = ticket[i];
        }
        return m;
    }


}
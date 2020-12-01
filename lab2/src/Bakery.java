public class Bakery extends Thread {
    private static int numThread = 61;
    int result;
    public static void main(String[] args) throws InterruptedException {
        BakeryLock lockFirst = new BakeryLock(numThread);
        ImprovedBakeryLock lockSecond = new ImprovedBakeryLock(numThread);

        Thread[] threads = new Thread[numThread];

        for(int i = 0; i < numThread; i++) {
            int b;
            if( i % 2 == 0)
                b = -1;
            else
                b = 1;
            if( i == numThread - 1)
                b = 0;
            threads[i] = new Thread((Runnable) new BakeryRunnable(lockSecond,b));
            //lockFirst.registerThread();
            threads[i].start();
        }
        for(int i = 0; i < numThread; i++) {
            threads[i].join();
            //lockFirst.unregisterThread();
        }
    }

}
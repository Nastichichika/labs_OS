public class BakeryRunnable implements  Runnable{
    BakeryLock lockFirst;
    ImprovedBakeryLock lockSecond;
    private static volatile int a = 0;
    int b;
    BakeryRunnable(ImprovedBakeryLock lock,  int b) {
        this.lockSecond = lock;
        this.b = b;
    }
    @Override
    public void run() {
        lockSecond.lock();
        for(int i = 0; i < 10; i++) {
            a += b;
            //System.out.println(Thread.currentThread().getName() + " " + a);
        }
        System.out.println(Thread.currentThread().getName() + " " + a);
        lockSecond.unlock();
    }
}

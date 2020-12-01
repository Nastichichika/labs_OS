public class BakeryLock {
    private int numberOfThreads;
    private static int[] tickets;
    BakeryLock(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
        tickets = new int[numberOfThreads];
        for(int i = 0; i < numberOfThreads; i++)
            tickets[i] = 0;

    }
    public void lock() {
        int id = (int) Thread.currentThread().getId() % numberOfThreads;
        tickets[id] = findMax() + 1;
        for (int i = 0; i < tickets.length; i++) {
            if (i == id) { continue; }
            while (tickets[i] != 0 && tickets[i] < tickets[id]) {Thread.yield(); }
        }
    }

    public void unlock() {
        int id = (int) Thread.currentThread().getId() % numberOfThreads;
        tickets[id] = 0;
    }
    private int findMax() {
        int m = tickets[0];
        for (int i = 1; i < tickets.length; i++) {
            if (tickets[i] > m)
                m = tickets[i];
        }
        return m;
    }
}

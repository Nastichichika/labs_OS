public class BakeryLock {
    public static final int numberOfThreads = 5;
    private static boolean[] choosing = new boolean[numberOfThreads];

    private static int[] tickets = new int[numberOfThreads];
    public void lock() {
        int id = 0;
        choosing[id] = true;
        tickets[id] = findMax() + 1;
        choosing[id] = false;
        for (int i = 0; i < tickets.length; i++) {
            if (i == id) { continue; }
            while (choosing[i]) { }

            while ((tickets[i] != 0) && ((tickets[i] < tickets[id]) || ((tickets[i] == tickets[id]) && (i < id)))) {
            }
        }
    }

    private void unlock(int i) {
        tickets[i] = 0;
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

import java.util.concurrent.atomic.AtomicIntegerArray;

public class ImrovedBakeryLock  {
    int id;
    public static final int numberOfThreads = 5;
    AtomicIntegerArray ticket = new AtomicIntegerArray(numberOfThreads);

    AtomicIntegerArray choosing = new AtomicIntegerArray(numberOfThreads);

    public void lock(int id)
    {
        //this.id = getId();
        choosing.set(id, 1);
        ticket.set(id, 1 + findMax());
        choosing.set(id, 0);
        for (int i = 0; i < ticket.length() - 1; ++i)
        {
            if (i != id)
            {
                while (choosing.get(i) == 1) { Thread.yield(); }
                while (ticket.get(i) != 0 && ( ticket.get(id) > ticket.get(i)  ||
                        (ticket.get(id) == ticket.get(i) && id > i)))
                { Thread.yield(); }
            }
        }
    }

    public void unlock(int pid)
    {
        ticket.set(pid, 0);
    }
    private int findMax() {
        int m = ticket.get(0);
        for (int i = 1; i < ticket.length(); i++) {
            if (ticket.get(i) > m)
                m = ticket.get(i);
        }
        return m;
    }
}

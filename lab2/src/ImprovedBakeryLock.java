import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class ImprovedBakeryLock {
    private static final int MAX = Integer.MAX_VALUE;
    private static volatile AtomicInteger[] ticket;
    private static volatile AtomicBoolean[] choosing;
    private final int numberOfThreads;

    ImprovedBakeryLock(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
        //ticket = new ArrayList<>(Arrays.asList(new AtomicInteger[numberOfThreads]));
        //choosing = new ArrayList<>(Arrays.asList(new AtomicBoolean[numberOfThreads]));
        choosing = new AtomicBoolean[numberOfThreads];
        ticket = new AtomicInteger[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            choosing[i] = new AtomicBoolean();
            ticket[i] = new AtomicInteger();
        }
        //Collections.fill(ticket, new AtomicInteger(0));
        //Collections.fill(choosing, new AtomicBoolean(false));
    }
    public void lock() {
        int id = (int) Thread.currentThread().getId() % numberOfThreads;
        //choosing.set(id, new AtomicBoolean(true));
        choosing[id] = new AtomicBoolean(true);

        int find = findMax();
        if(find != MAX - 1) {
            ticket[id] = new AtomicInteger(1 + find);

            //ticket.set(id, new AtomicInteger(1 + find));
        }
        else
            ticket[id] = new AtomicInteger(0);
            //ticket.set(id, new AtomicInteger(0));
        choosing[id] = new AtomicBoolean(false);
        //choosing.set(id, new AtomicBoolean(false));


        //int size = ticket.size();
        int size = ticket.length;
        for (int i = 0; i < size - 1; ++i)
        {
            if (i != id)
            {
                while (choosing[i].get()) { Thread.yield(); }
                while (ticket[i].get() != 0 && ( ticket[id].get() > ticket[i].get()  ||
                        (ticket[id].equals(ticket[i]) && id > i)))
                { Thread.yield(); }
                //while (choosing.get(i).get()) { Thread.yield(); }
                //while (ticket.get(i).get() != 0 && ( ticket.get(id).get() > ticket.get(i).get()  ||
                //        (ticket.get(id).equals(ticket.get(i)) && id > i)))
                //{ Thread.yield(); }
            }
        }
    }

    public void unlock()
    {
        int id = (int) Thread.currentThread().getId() % numberOfThreads;
        //ticket.set(id, new AtomicInteger(0));
        ticket[id] = new AtomicInteger(0);
    }
    private int findMax() {
        //int m = ticket.get(0).get();
        //int size = ticket.size();
       // for (int i = 1; i < size; i++) {
        //    if (ticket.get(i).get() > m)
        //        m = ticket.get(i).get();
        //}
        int m = ticket[0].get();
        int size = ticket.length;
        for (int i = 1; i < size; i++) {
            if (ticket[i].get() > m)
                m = ticket[i].get();
        }
        return m;
    }
}

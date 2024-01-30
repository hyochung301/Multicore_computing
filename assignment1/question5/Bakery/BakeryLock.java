package question5.Bakery;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicBoolean;

public class BakeryLock implements Lock {

    int N;
    AtomicBoolean[] choosing;
    AtomicInteger[] number;
    
    public BakeryLock(int numThreads) {
        N = numThreads;
        choosing = new AtomicBoolean[N];
        number = new AtomicInteger[N];
        for (int i = 0; i < N; i++) {
            choosing[i] = new AtomicBoolean();
            choosing[i].set(false);
            number[i] = new AtomicInteger();
            number[i].set(0);
        }
    }

    public void lock(int pid) {
        // choose number
        choosing[pid].set(true);
        for (int j = 0; j < N; j++) {
            if (number[j].get() > number[pid].get()) { number[pid].set(number[j].get()); }
        }
        number[pid].set(number[pid].get()+1);
        // System.out.println(String.format("%d chose num %d", pid, number[pid].get()));
        choosing[pid].set(false);
        // wait in line
        int wt = 0;
        for (int j = 0; j < N; j++) {
            // while (choosing[j]) {
            //     // if ((++wt % 100000) == 0) System.out.println(String.format("%d waiting for %d to choose", pid, j));
            // }
            while (
                   (choosing[j].get()) || 
                   ((number[j].get() != 0) && 
                   ((number[j].get() < number[pid].get()) || ((number[j].get() == number[pid].get()) && (j < pid))))
                  )
            {
                // if ((++wt % 100000) == 0) System.out.println(String.format("%d waiting in busy for %d", pid, j));
            }
        }
    }

    public void unlock(int pid) {
        // System.out.println(String.format("%d dropped %d", pid, number[pid].get()));
        number[pid].set(0);
    }
}

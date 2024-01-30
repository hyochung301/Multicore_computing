package question5.ReentrantLock;

import java.util.concurrent.locks.*;
import java.util.ArrayList;

public class PIncrement implements Runnable {

    private final ReentrantLock relock = new ReentrantLock();
    private int counter, N;

    public PIncrement(int c, int NT) {
        counter = c; N = NT;
    }

    public int getCounter() {return counter;}

    public static int parallelIncrement(int c, int numThreads) {

        if (numThreads < 1) return 0; // why?

        PIncrement runner = new PIncrement(c, numThreads);

        ArrayList<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < numThreads; i++) {
            threads.add(new Thread(runner));
        }

        long start = System.nanoTime();

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        long end = System.nanoTime();

        System.out.println("Elapsed time: " + (end - start) / 1_000_000 + " ms");

        return runner.getCounter();
        
    }

    public void run() {
        for (int i = 0; i < (1200000/N); i++) {
            relock.lock();
            counter++;
            relock.unlock();
        }
    }
}

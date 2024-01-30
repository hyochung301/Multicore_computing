package question5.Bakery;

import java.util.ArrayList;
import java.util.HashMap;

public class PIncrement implements Runnable {

    private int counter, N;
    private Lock bakery;
    public HashMap<Thread, Integer> tids; // hate this but oh well!

    public PIncrement(int c, int NT) {
        counter = c; N = NT;
        bakery = new BakeryLock(N);
        tids = new HashMap<Thread, Integer>();
    }

    public int getCounter() {return counter;}

    public static int parallelIncrement(int c, int numThreads) {

        if (numThreads < 1) return 0; // why?

        PIncrement runner = new PIncrement(c, numThreads);

        ArrayList<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < numThreads; i++) {
            Thread t = new Thread(runner);
            threads.add(t);
            runner.tids.put(t, i);
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
        // int thisinc = 0;
        int tid = tids.get(Thread.currentThread());
        for (int i = 0; i < (1200000/N); i++) {
            bakery.lock(tid);
            counter++;
            // thisinc++;
            // System.out.println(String.format("%d incing ct to %d", tid, counter));
            bakery.unlock(tid);
        }
        // System.out.println(String.format("%d did %d incs", tid, thisinc));
    }
}


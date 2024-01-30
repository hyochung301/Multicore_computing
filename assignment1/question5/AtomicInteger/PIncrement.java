package question5.AtomicInteger;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;



public class PIncrement implements Runnable {
    private static int numThreads;
    private static int numIncrements = 1200000;
    private static AtomicInteger atomicvalue;

    public static int parallelIncrement(int c, int numThreads) {
        if (numThreads <= 0) {
            return numIncrements; // no thread to perform parllel increment
        }
        atomicvalue = new AtomicInteger(c);

        PIncrement runnable = new PIncrement();
        Set<Thread> threads = new HashSet<Thread>();
        long startTime = System.nanoTime();  // Start time measurement


        // create threads and start
        for (int i = 0; i < numThreads; i++) {
            Thread t = new Thread(runnable);
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.nanoTime();  // End time measurement
        System.out.println("Elapsed time: " + (endTime - startTime) / 1_000_000 + " ms");

        return atomicvalue.get();
    }


    @Override
    public void run() {
        int thread_task = numIncrements / numThreads;
        int counter = 0;
        for (int i = 0; i < thread_task; i++) {
            int old = atomicvalue.get();
            if (!atomicvalue.compareAndSet(old, old + 1)) {
                i--;
            }
            counter++;
        }
        System.out.println("Thread " + Thread.currentThread().getId() + " finished." + " Counter: " + counter);
    }
}

package question5.ReentrantLock;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class PIncrement implements Runnable {
    private static int numThreads;
    private static int numIncrements = 1200000;
    private static ReentrantLock lock;
    private static int c_variable;

    public static int parallelIncrement(int c, int numThreads) {
        if (numThreads <= 0) {
            return numIncrements; // no thread to perform parallel increment
        }
        c_variable = c;

        PIncrement runnable = new PIncrement();

        Set<Thread> threads = new HashSet<Thread>();

        // create threads and start
        for (int i = 0; i < numThreads; i++) {
            Thread t = new Thread(runnable);
            threads.add(t);
            t.start();
        }

        // wait for all threads to finish
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return c_variable;
    }


    public void run() {
        int counter = 0;
        int thread_task = numIncrements / numThreads;
        for (int i = 0; i < thread_task; i++) {
            lock.lock();
            try {
                c_variable++;
                counter++;
            } finally {
                lock.unlock();
            }
        }
        System.out.println("Thread " + Thread.currentThread().getId() + " finished." + " Counter: " + counter);
    }
}
package question4;

import java.util.concurrent.*;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Set;

public class Frequency implements Callable<Integer> {
    private final int x;
    private final int[] A;

    public Frequency(int x, int[] A) {
        this.x = x;
        this.A = A;
    }

    public static int parallelFreq(int x, int[] A, int numThreads) {
        if (A == null || numThreads <= 0) {
            return -1;
        } // meet the condition of "return -1 if the input is not valid"

        // If the array size is smaller than the number of threads, assign one task to one thread
        if (A.length < numThreads) {
            numThreads = A.length;
        }

        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads); // specify number of threads into a thread pool
        Set<Future<Integer>> futures = new HashSet<>(); // set of futures to keep track of threads results
        int subarraySize = A.length / numThreads; // size of each subarray each thread will process

        // submit tasks to threadPool
        for (int i = 0; i < numThreads; i++) {
            int start = i * subarraySize;
            int end = Math.min(start + subarraySize, A.length);
            int[] subArray = Arrays.copyOfRange(A, start, end); //create a subarray by passing in the og array, start and end inex
            Future<Integer> future = threadPool.submit(new Frequency(x, subArray)); // submit a task to threadPool
            futures.add(future);
        }

        // get results from futures
        int sum = 0;
        for (Future<Integer> future : futures) {
            try {
                sum += future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        threadPool.shutdown();

        return sum;
    }

    public Integer call() {
        int count = 0;
        for (int value : A) {
            if (value == x) {
                count++;
            }
        }
        System.out.println("Count in call(): " + count); // print count from call()

        return count;
    }
}
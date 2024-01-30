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
        if (A == null || numThreads <= 0 || A.length == 0) {
            return -1;
        } // meet the condition of "return -1 if the input is not valid"

        // If the array size is smaller than the number of threads, assign one task to one thread
        if (A.length < numThreads) {
            numThreads = A.length;
        }

        // because num elements may not be evenly divisible by nthreads, subarrays may vary in size
        // so I'll keep an array of subsizes instead and spread the remainder across this array
        int[] subsizes = new int[numThreads];

        for (int i = 0; i < subsizes.length; i++) {
            subsizes[i] = A.length / numThreads;
        }

        if ((A.length % numThreads) != 0) {
            for (int i = 0; i < (A.length%numThreads); i++) {
                subsizes[i]++;
            }
        }

        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads); // specify number of threads into a thread pool
        Set<Future<Integer>> futures = new HashSet<>(); // set of futures to keep track of threads results

        // then we'll have to advance this index pointer to track the start index rather than the simple i * subarraysize
        int begini = 0;

        // submit tasks to threadPool
        for (int i = 0; i < numThreads; i++) {
            int start = begini;
            int end = Math.min(start + subsizes[i], A.length);
            int[] subArray = Arrays.copyOfRange(A, start, end); //create a subarray by passing in the og array, start and end inex
            Future<Integer> future = threadPool.submit(new Frequency(x, subArray)); // submit a task to threadPool
            futures.add(future);
            begini = end;
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
        // System.out.println("Count in call(): " + count); // print count from call()

        return count;
    }
}
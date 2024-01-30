package question5.Bakery;

public class BakeryLock implements Lock {

    int N;
    boolean[] choosing;
    int[] number;
    
    public BakeryLock(int numThreads) {
        N = numThreads;
        choosing = new boolean[N];
        number = new int[N];
        for (int i = 0; i < N; i++) {
            choosing[i] = false;
            number[i] = 0;
        }
    }

    public void lock(int pid) {
        // choose number
        choosing[pid] = true;
        for (int j = 0; j < N; j++) {
            if (number[j] > number[pid]) { number[pid] = number[j]; }
        }
        number[pid]++;
        System.out.println(String.format("%d chose num %d", pid, number[pid]));
        choosing[pid] = false;
        // wait in line
        int wt = 0;
        for (int j = 0; j < N; j++) {
            // while (choosing[j]) {
            //     // if ((++wt % 100000) == 0) System.out.println(String.format("%d waiting for %d to choose", pid, j));
            // }
            while (
                   (choosing[j]) || 
                   ((number[j] != 0) && 
                   ((number[j] < number[pid]) || ((number[j] == number[pid]) && (j < pid))))
                  )
            {
                // if ((++wt % 100000) == 0) System.out.println(String.format("%d waiting in busy for %d", pid, j));
            }
        }
    }

    public void unlock(int pid) {
        System.out.println(String.format("%d dropped %d", pid, number[pid]));
        number[pid] = 0;
    }
}

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;

public abstract class LLP {
    // Feel free to add any methods here. Common parameters (e.g. number of processes)
    // can be passed up through a super constructor. Your code will be tested by creating
    // an instance of a sub-class, calling the solve() method below, and then calling the
    // sub-class's getSolution() method. You are free to modify anything else as long as
    // you follow this API (see SimpleTest.java)

    private ExecutorService workers;
    private ArrayList<ParallelAdvancer> advancers;

    protected int[] G;
    protected final int state_dimension;

    public LLP(int sd) {
        state_dimension = sd;
        G = new int[sd];
        advancers = new ArrayList<ParallelAdvancer>();
        for (int i = 0; i < sd; i++) {advancers.add(new ParallelAdvancer(i));}
        workers = Executors.newFixedThreadPool(state_dimension);
    }

    // Checks whether process j is forbidden in the state vector G
    public abstract boolean forbidden(int j);

    // Advances on process j
    public abstract void advance(int j);

    public void solve() {
        // Implement this method. There are many ways to do this but you
        // should follow the following basic steps:
        // 1. Compute the forbidden states
        // 2. Advance on forbidden states in parallel
        // 3. Repeat 1 and 2 until there are no forbidden states

        while (true) {
            ArrayList<Integer> forbidden = new ArrayList<Integer>();
            for (int i = 0; i < state_dimension; i++) {
                if (forbidden(i))
                    forbidden.add(i);
            }
            if (forbidden.size() == 0) break;

            for (Integer f : forbidden) {
                workers.execute(advancers.get(f));
            }
        }

        workers.shutdown();
        while (!workers.isTerminated()) {}
    }


    /*
        
    */
    private class ParallelAdvancer implements Runnable {
        
        private final int j;

        public ParallelAdvancer(int i) {j = i;}

        @Override
        public void run() {
            LLP.this.advance(j);
        }
    }
}

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;
import java.util.concurrent.Callable;

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
        for (int i = 0; i < sd; i++) {
            G[i] = 0;
            advancers.add(new ParallelAdvancer(i));
        }
        workers = Executors.newFixedThreadPool(state_dimension);
    }

    public LLP(int sd, int ss) {
        state_dimension = sd;
        G = new int[sd];
        advancers = new ArrayList<ParallelAdvancer>();
        for (int i = 0; i < sd; i++) {
            G[i] = ss;
            advancers.add(new ParallelAdvancer(i));
        }
        workers = Executors.newFixedThreadPool(state_dimension);
    }

    // Checks whether process j is forbidden in the state vector G
    public abstract boolean forbidden(int j);

    // Advances on process j
    public abstract void advance(int j);

    public static void parr(int[] arr) {for (int a : arr){System.out.print(a); System.out.print(", ");} System.out.println();}

    public void solve() {
        // Implement this method. There are many ways to do this but you
        // should follow the following basic steps:
        // 1. Compute the forbidden states
        // 2. Advance on forbidden states in parallel
        // 3. Repeat 1 and 2 until there are no forbidden states

        // boolean forbidden_entries = true;
        // int n = 0;
        // while (forbidden_entries) {
        //     // everyone check if forbidden and advance in pllel
        //     try { 
        //         workers.invokeAll(advancers); 
        //     } catch (InterruptedException e) {e.printStackTrace();}
        //     // break the loop if nobody is forbidden
        //     forbidden_entries = false;
        //     for (int i = 0; i < state_dimension; i++) {
        //         if (advancers.get(i).forbidden()) {
        //             forbidden_entries = true; break;
        //         }
        //     }
        // }

        // workers.shutdown();
        // while (!workers.isTerminated()) {}
        while (true) {
            int fn = 0;
            boolean[] fb = new boolean[state_dimension];
            for (int i = 0; i < state_dimension; i++){
                if (forbidden(i)) {
                    fn++;
                    fb[i] = true;
                }
            }
            if (fn==0) break;
            for (int i = 0; i < state_dimension; i++){
                if (fb[i]) advance(i);
            }
        }

    }

    public int[] readG() {return G;} // for testing

    /*
        attaches to one elem of state vector
        checks forbidden and advances in own thread
    */
    private class ParallelAdvancer implements Callable<Void> {
        private final int j;
        private boolean forb;
        public ParallelAdvancer(int i) {j = i; forb = true;}

        @Override
        public Void call() throws Exception {
            forb = LLP.this.forbidden(j);
            if (forb) LLP.this.advance(j); 
            return null;
        }

        public boolean forbidden() {return forb;}
    }
}

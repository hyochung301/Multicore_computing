public class JobScheduling extends LLP {

    private final int[] time;
    private final int[][] pre;
    private int[] advancement;

    // time[i] is the amount of time job i takes to complete
    // prerequisites[i] is a list of jobs that job i depends on
    public JobScheduling(int[] time, int[][] prerequisites) {
        super(time.length);
        this.time = time;
        pre = prerequisites;
        G = time.clone();
        advancement = new int[state_dimension];
    }

    @Override
    public boolean forbidden(int j) {
        for (int k = 0; k < pre[j].length; k++) {
            int i = pre[j][k];
            if (G[j] < (G[i] + time[j])) {
                advancement[j] = (G[i] + time[j]) - G[j];
                return true;
            }
        }
        return false;
    }

    @Override
    public void advance(int j) {
        G[j] += advancement[j];
    }

    // This method will be called after solve()
    public int[] getSolution() {
        return G;
    }
}

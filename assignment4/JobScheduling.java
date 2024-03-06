public class JobScheduling extends LLP {

    // time[i] is the amount of time job i takes to complete
    // prerequisites[i] is a list of jobs that job i depends on
    public JobScheduling(int[] time, int[][] prerequisites) {
        super(1); //TODO replace w correct dimension
    }

    @Override
    public boolean forbidden(int j) {
        return false;
    }

    @Override
    public void advance(int j) {

    }

    // This method will be called after solve()
    public int[] getSolution() {
        // Return the completion time for each job
        return new int[]{};
    }
}

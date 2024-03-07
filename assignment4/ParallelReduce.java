public class ParallelReduce extends LLP {

    private int[] A;

    // A is an input array that we want to compute the reduction for
    public ParallelReduce(int[] A) {
        super(A.length); 
        this.A = A;
    }

    @Override
    public boolean forbidden(int j) {
        if (j==0) return false;
        if (j < state_dimension/2) {
            return G[j] < (G[2*j] + G[2*j + 1]);
        } else {
            int t1 = (A[2*j - state_dimension]);
            int t2 = (A[2*j - state_dimension + 1]);
            return G[j] < t1+t2;
        }
    }

    @Override
    public void advance(int j) {
        System.out.println(String.format("%d ADVANCING", j));
        if (j < state_dimension/2) {
            G[j] = (G[2*j] + G[2*j + 1]);
        } else {
            G[j] = (A[2*j - state_dimension] + A[2*j - state_dimension + 1]);
        }
    }

    // This method will be called after solve()
    public int[] getSolution() {
        // Trim the state vector to only the reduce elements
        // Your result should have n-1 elements
        int[] r = new int[state_dimension-1];
        for (int i = 1; i < state_dimension; i++) {
            r[i-1] = G[i];
        }
        return r;
    }
}

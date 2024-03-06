public class ParallelReduce extends LLP {

    // A is an input array that we want to compute the reduction for
    public ParallelReduce(int[] A) {
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
        // Trim the state vector to only the reduce elements
        // Your result should have n-1 elements
        return new int[]{};
    }
}

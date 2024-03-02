public class StableMarriage extends LLP {

    // mprefs[i][k] is man i's kth choice
    // wprefs[i][k] is woman i's kth choice
    public StableMarriage(int[][] mprefs, int[][] wprefs) {
        super();
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
        // Return the partner for each man
        return new int[]{};
    }
}

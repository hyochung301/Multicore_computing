public class StableMarriage extends LLP {

    // mprefs[i][k] is man i's kth choice
    // wprefs[i][k] is woman i's kth choice

    private final int[][] wprefs;
    private final int[][] mprefs;
    private int[] wives;

    // wranks[i][k] is woman i's ranking of man k
    private int[][] wranks;

    public StableMarriage(int[][] mprefs, int[][] wprefs) {
        super(mprefs.length);
        this.wprefs = wprefs;
        this.mprefs = mprefs;
        wives = new int[state_dimension];
        wranks = new int[state_dimension][state_dimension];
        // pretend I parallelized creating these inverse lists
        for (int woman = 0; woman < state_dimension; woman++) {
            for (int rank = 0; rank < state_dimension; rank++) {
                wranks[woman][wprefs[woman][rank]] = rank;
            }
        }
    }

    @Override
    public boolean forbidden(int j) {
        int z = mprefs[j][G[j]];
        wives[j] = z;
        for (int i = 0; i < state_dimension; i++) {
            for (int k = 0; k <= G[i]; k++) {
                if ((z == mprefs[i][k]) && (wranks[z][i] < wranks[z][j])) return true;
            }
        }
        return false;
    }

    @Override
    public void advance(int j) {
        G[j]++;
        wives[j] = mprefs[j][G[j]];
    }

    // This method will be called after solve()
    public int[] getSolution() {
        return wives;
    }
}

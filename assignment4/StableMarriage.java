public class StableMarriage extends LLP {
    // 5ms with given testStableMarriage()
    // mprefs[i][k] is man i's kth choice
    // wprefs[i][k] is woman i's kth choice

    private final int[][] wprefs;
    private final int[][] mprefs;
    private int[] wives;

    public StableMarriage(int[][] mprefs, int[][] wprefs) {
        super(mprefs.length);
        this.wprefs = wprefs;
        this.mprefs = mprefs;
        wives = new int[state_dimension];
    }

    @Override
    public boolean forbidden(int j) {
        int z = mprefs[j][G[j]];
        for (int i = 0; i < state_dimension; i++) {
            for (int k = 0; k <= G[i]; k++) {
                if ((z == mprefs[i][k]) && (wprefs[z][i] < wprefs[z][j])) return true;
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

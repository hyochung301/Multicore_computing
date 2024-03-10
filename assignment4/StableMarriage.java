public class StableMarriage extends LLP {

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

    public static int idof(int[] arr, int v){for (int i = 0; i < arr.length; i++){if (arr[i]==v) return i;}return -1;}

    @Override
    public boolean forbidden(int j) {
        int z = mprefs[j][G[j]];
        wives[j] = z;
        for (int i = 0; i < state_dimension; i++) {
            for (int k = 0; k <= G[i]; k++) {
                int z_rank_i = idof(wprefs[z], i);
                int z_rank_j = idof(wprefs[z], j);
                if ((z == mprefs[i][k]) && (z_rank_i < z_rank_j)) {
                    // if (j==0) {
                    //     System.out.println(String.format(
                    //         "0 is w %d but %d ranks her %d and she ranks him %d as opposed to %d",
                    //         z, i, k, z_rank_i, z_rank_j
                    //     ));
                    //     System.out.println(String.format(
                    //         "man %d top rank is %d but he's on %d prop", i, mprefs[i][0], G[i]
                    //     ));
                    // }
                    return true;
                }
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

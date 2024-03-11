public class ParallelPrefix extends LLP {

    // A is an input array that we want to compute the prefix scan for
    // S is the pre-computed summation tree (reduction), computed using LLP-Reduce

    private int[] A;
    private int[] S;
    private final int n;

    private int to_zero(int one_indexed) {return one_indexed-1;}

    public ParallelPrefix(int[] A, int[] S) {
        super(A.length + S.length, Integer.MIN_VALUE);
        this.A = A; this.S = S;
        n = A.length;
    }

    @Override
    public boolean forbidden(int j) {
        j++; // convert to one indexed
        if (j == 1) {
            return G[to_zero(j)] < (0);
        }

        if ((j&0x01) == 0) {
            return (G[to_zero(j)] < (G[to_zero(j/2)]));
        }

        if (j < n) {
            return G[to_zero(j)] < (S[to_zero(j-1)] + G[to_zero(j/2)]);
        }

        return G[to_zero(j)] < (A[to_zero(j-n)] + G[to_zero(j/2)]);
    }

    @Override
    public void advance(int j) {
        j++; // convert to one indexed
        if (j == 1) {
            G[to_zero(j)] = 0; return;
        }

        if ((j&0x01) == 0) {
            G[to_zero(j)] = G[to_zero(j/2)]; return;
        }

        if (j < n) {
            G[to_zero(j)] = S[to_zero(j-1)] + G[to_zero(j/2)]; 
            return;
        }
        if (j > n) {
            G[to_zero(j)] = A[to_zero(j-n)] + G[to_zero(j/2)];
        }
    }

    public int[] getSolution() {
        int[] res = new int[n];
        for (int i = state_dimension-1; i >= state_dimension-n; i--) {
            res[i-(S.length)] = G[i];
        }
        return res;
    }
}

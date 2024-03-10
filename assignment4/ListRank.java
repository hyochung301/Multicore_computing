public class ListRank extends LLP {

    // 5ms with given testListRanking()
    private int[] parent;

    private int[] next;

    private int r;

    // parent[i] tells us the index of the parent of node i
    // the root r has parent[r] = -1
    public ListRank(int[] parent) {
        super(parent.length,1);
        this.parent = parent;
        // this for loop is bad but we need to find the parent node 
        // and I don't know if we can assume a tree structure
        for (int i = 0; i < parent.length; i++) {
            if (parent[i]==-1) {
                r = i; break;
            }
        }
        G[r] = 0;
    }

    @Override
    public boolean forbidden(int j) {
        return (parent[j] != r) && (parent[j] != -1);
    }

    @Override
    public void advance(int j) {
        G[j] += G[parent[j]];
        parent[j] = parent[parent[j]];
    }

    // This method will be called after solve()
    public int[] getSolution() {
        return G;
    }
}

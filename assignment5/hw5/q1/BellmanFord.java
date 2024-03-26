public class BellmanFord extends LLP {
    // You may be given inputs with negative weights, but no negative cost cycles
    public BellmanFord(int[][] adjMatrix, int source) {
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
        // Return the vector of shortest path costs from source to each vertex
        // If a vertex is not connected to the source then its cost is Integer.MAX_VALUE
        return new int[]{};
    }
}

public class ConnectedComponents extends LLP {
    public ConnectedComponents(int[][] adjMatrix) {
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
        // Return the vector where the i^th entry is the index j where
        // j is the largest vertex label contained in the component containing 
        // vertex i
        return new int[]{};
    }
}

import org.junit.Test;
import static org.junit.Assert.*;

public class SimpleTest {

    final int[][] ADJ_MATRIX = {
      // 0,  1,  2,  3,  4,  5,  6,  7,  8 (vertex labels)
        {0,  0,  0,  0,  0,  0,  0,  1,  0}, // 0
        {0,  0,  7,  9,  0,  0,  14, 0,  0}, // 1
        {0,  7,  0,  10, 15, 0,  0,  0,  0}, // 2
        {0,  9,  10, 0,  11, 0,  2,  0,  0}, // 3
        {0,  0,  15, 11, 0,  6,  0,  0,  0}, // 4
        {0,  0,  0,  0,  6,  0,  9,  0,  0}, // 5
        {0,  14, 0,  2,  0,  9,  0,  0,  0}, // 6
        {1,  0,  0,  0,  0,  0,  0,  0,  7}, // 7
        {0,  0,  0,  0,  0,  0,  0,  7,  0}  // 8
    };

    final int[] COMPONENTS = {8, 6, 6, 6, 6, 6, 6, 8, 8};
    final int[] SOURCE1_SPATH_COSTS = {Integer.MAX_VALUE, 0, 7, 9, 20, 20, 11, Integer.MAX_VALUE, Integer.MAX_VALUE};
    
    @Test
    public void testConnectedComponents() {
        ConnectedComponents cc = new ConnectedComponents(ADJ_MATRIX);
        cc.solve();
        int[] components = cc.getSolution();
        assertArrayEquals(components, COMPONENTS);
    }

    @Test
    public void testBellmanFord() {
        int source = 1;
        BellmanFord bf = new BellmanFord(ADJ_MATRIX, source);
        bf.solve();
        int[] costs = bf.getSolution();
        assertArrayEquals(costs, SOURCE1_SPATH_COSTS);
    }
    
    @Test
    public void testJohnsons() {
        int source = 1;
        Johnsons johnsons = new Johnsons(ADJ_MATRIX, source);
        johnsons.solve();
        int[] costs = johnsons.getSolution();
        assertArrayEquals(costs, SOURCE1_SPATH_COSTS);
    }
}

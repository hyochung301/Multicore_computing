import org.junit.Test;
import static org.junit.Assert.*;

public class SimpleTest {

    @Test
    public void testLLPsuper() {
        LLP llp = new LLP(8) {

            public final int[] sol = {4, 9, 0, 8, 13, 2, 1, 99};

            @Override
            public boolean forbidden(int j) {
                return G[j] < sol[j];
            }
            @Override
            public void advance(int j) {
                G[j]++;
            }

        };

        llp.solve();

        final int[] expected_sol = {4, 9, 0, 8, 13, 2, 1, 99};

        assertArrayEquals(expected_sol, llp.readG());

    }

    @Test
    public void testJobScheduling() {
        int[] time = { 2, 3, 4, 1, 5 };
        int[][] prerequisites = {
                {},         // Job 0 has no prerequisites
                { 0 },      // Job 1 depends on Job 0
                { 0, 1 },   // Job 2 depends on Job 0 and Job 1
                { 2 },      // Job 3 depends on Job 2
                { 3 }       // Job 4 depends on Job 3
        };
        int[] expected = { 2, 5, 9, 10, 15 };

        JobScheduling js = new JobScheduling(time, prerequisites);
        js.solve();
        int[] completion_times = js.getSolution();
        assertArrayEquals(expected, completion_times);
    }

    // @Test
    // public void testStableMarriage() {
    //     int[][] mprefs = {
    //             { 1, 2, 0 },
    //             { 0, 1, 2 },
    //             { 0, 1, 2 }
    //     };
    //     int[][] wprefs = {
    //             { 1, 0, 2 },
    //             { 1, 2, 0 },
    //             { 0, 1, 2 }
    //     };
    //     int[] expected = { 2, 0, 1 };

    //     StableMarriage sm = new StableMarriage(mprefs, wprefs);
    //     sm.solve();
    //     int[] matching = sm.getSolution();
    //     assertArrayEquals(expected, matching);
    // }

    @Test
    public void testParallelReduce() {
        int[] A = { 1, 2, 3, 4 };
        int[] expected = { 10, 3, 7 };

        ParallelReduce pr = new ParallelReduce(A);
        pr.solve();
        int[] reduce = pr.getSolution();
        assertArrayEquals(expected, reduce);
    }

    // @Test
    // public void testParallelPrefix() {
    //     int[] A = { 1, 2, 3, 4, 5, 6, 7, 8 };
    //     int[] expected = { 0, 1, 3, 6, 10, 15, 21, 28 };

    //     ParallelReduce pr = new ParallelReduce(A);
    //     pr.solve();
    //     int[] S = pr.getSolution();

    //     ParallelPrefix pp = new ParallelPrefix(A, S);
    //     pp.solve();
    //     int[] prefix = pp.getSolution();
    //     assertArrayEquals(expected, prefix);
    // }

    // @Test
    // public void testListRanking() {
    //     int[] parents = { 2, 4, 8, 8, -1, 2, 1, 6, 4 };
    //     int[] expected = { 3, 1, 2, 2, 0, 3, 2, 3, 1 };

    //     ListRank lr = new ListRank(parents);
    //     lr.solve();
    //     int[] distances = lr.getSolution();
    //     assertArrayEquals(expected, distances);
    // }

    // @Test
    // public void testTransitiveClosure() {
    //     boolean[][] graph = {
    //             { true, true, false, true },    // node 0 has edges to nodes 0, 1, and 3
    //             { false, true, true, false },   // node 1 has edges to nodes 2 and 3
    //             { false, false, true, true },   // node 2 has edges to nodes 2 and 3
    //             { false, false, false, true }   // node 3 has an edge to node 3
    //     };
    //     boolean[][] expected = {
    //             { true, true, true, true },
    //             { false, true, true, true },
    //             { false, false, true, true },
    //             { false, false, false, true }
    //     };

    //     TransitiveClosure tc = new TransitiveClosure(graph);
    //     tc.solve();
    //     boolean[][] transitiveClosure = tc.getSolution();
    //     assertArrayEquals(expected, transitiveClosure);
    // }
}

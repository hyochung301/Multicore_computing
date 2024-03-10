import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;

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

    @Test
    public void testStableMarriage() {
        int[][] mprefs = {
                { 1, 2, 0 },
                { 0, 1, 2 },
                { 0, 1, 2 }
        };
        int[][] wprefs = {
                { 1, 0, 2 },
                { 1, 2, 0 },
                { 0, 1, 2 }
        };
        int[] expected = { 2, 0, 1 };

        StableMarriage sm = new StableMarriage(mprefs, wprefs);
        sm.solve();
        int[] matching = sm.getSolution();
        // System.out.println("Expect:");
        // LLP.parr(expected);
        // System.out.println("Got:");
        // LLP.parr(matching);
        assertArrayEquals(expected, matching);
    }

    public static int[][] readArrayFromFile(String filePath) throws IOException {
        List<int[]> list = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        
        for (String line : lines) {
            // Remove braces and split by comma
            line = line.trim();  // Remove leading and trailing spaces
            if(line.startsWith("{")) {
                line = line.substring(1); // Remove starting brace
            }
            if(line.endsWith("},")) {
                line = line.substring(0, line.length() - 2); // Remove ending brace and comma
            } else if(line.endsWith("}")) {
                line = line.substring(0, line.length() - 1); // Remove ending brace
            }

            // Skip empty or non-data lines
            if (line.isEmpty() || line.equals("};")) continue;

            String[] numbers = line.split(",\\s*");
            int[] intNumbers = new int[numbers.length];
            for (int i = 0; i < numbers.length; i++) {
                intNumbers[i] = Integer.parseInt(numbers[i]);
            }
            list.add(intNumbers);
        }

        // Convert list to array
        int[][] array = new int[list.size()][];
        array = list.toArray(array);
        return array;
    }

    private static int idof(int[] arr, int v) {
        for (int i = 0; i < arr.length; i++){if (arr[i]==v) return i;}
        return -1;
    }

    private static boolean pairStable(int[][] wprefs, int[][] mprefs, int[] match, int s, int u, int sp, int up) {

        // make these negative so higher pref > lower pref
        int s_ranks_u =   -idof(mprefs[s], u);
        int s_ranks_up =  -idof(mprefs[s], up);
        
        int sp_ranks_u =  -idof(mprefs[sp], u);
        int sp_ranks_up = -idof(mprefs[sp], up);

        int u_ranks_s =   -idof(wprefs[u], s);
        int u_ranks_sp =  -idof(wprefs[u], sp);

        int up_ranks_s =  -idof(wprefs[up], s);
        int up_ranks_sp = -idof(wprefs[up], sp);

        return !(
                    (
                        sp_ranks_u > sp_ranks_up && u_ranks_sp > u_ranks_s
                    )
                    ||  // if either of these conditions exist, unstable
                    (
                        s_ranks_up > s_ranks_u && up_ranks_s > up_ranks_sp
                    )
                );

    }

    public boolean isStableMatching(int[][] wprefs, int[][] mprefs, int[] match) {
        /* TODO implement this function */
        /* Totally brute force approach */

        // brute force check every pair combo
        // for each student
        for (int s = 0; s < mprefs.length; s++) {
            int u = match[s];
            // within this loop, s and u are a pair
            // if u is -1, s didnt get one, continue
            if (u == -1) {
                continue;
            }
            for (int sp = 0; sp < mprefs.length; sp++) {
                int up = match[sp];
                if (up == -1) continue;
                if (s >= sp) continue; // dont check the same pair or repeat a previous check
                // in this block, we have 2 pairs (s, u) and (sp, up) we need to check for insta

                if (!(pairStable(wprefs, mprefs, match, s, u, sp, up))) {
                    int s_ranks_u =   -idof(mprefs[s], u);
                    int s_ranks_up =  -idof(mprefs[s], up);
                    
                    int sp_ranks_u =  -idof(mprefs[sp], u);
                    int sp_ranks_up = -idof(mprefs[sp], up);

                    int u_ranks_s =   -idof(wprefs[u], s);
                    int u_ranks_sp =  -idof(wprefs[u], sp);

                    int up_ranks_s =  -idof(wprefs[up], s);
                    int up_ranks_sp = -idof(wprefs[up], sp);

                    System.out.println(String.format(
                        "FAIL!\n%d is with %d, but %d and %d create inst.", s,u,sp,up
                    ));
                    System.out.println(String.format(
                        "s rank u: %d\ns rank up: %d\nsp rank u: %d\nsp rank up: %d\nu rank s: %d\nu rank sp: %d\nup rank s: %d\nup rank sp: %d\n",
                        s_ranks_u, s_ranks_up, sp_ranks_u, sp_ranks_up, u_ranks_s, u_ranks_sp, up_ranks_s, up_ranks_sp
                    ));
                    return false;
                }

            }
        }
 
        return true;
    }

    @Test
    public void testStableMarriageHUGE() {
        int[][] wprefs = new int[1][1];
        int[][] mprefs = new int[1][1];
        try {
            mprefs = readArrayFromFile("mprefs.c");
            wprefs = readArrayFromFile("wprefs.c");
        } catch (Exception e) {e.printStackTrace();}
        int[] expected = {92, 22, 14, 16, 20, 35, 61, 54, 18, 63, 78, 98, 25, 91, 28, 90, 99, 49, 4, 43, 62, 17, 80, 34, 48, 86, 94, 33, 64, 93, 51, 70, 69, 75, 19, 31, 45, 68, 81, 26, 7, 27, 42, 40, 67, 65, 44, 74, 38, 50, 37, 1, 36, 46, 85, 11, 83, 2, 39, 3, 77, 89, 53, 52, 58, 21, 79, 9, 60, 56, 97, 73, 15, 84, 30, 10, 59, 29, 5, 0, 71, 55, 96, 23, 95, 66, 6, 24, 47, 82, 32, 57, 87, 13, 41, 76, 12, 72, 88, 8};

        StableMarriage sm = new StableMarriage(mprefs, wprefs);
        sm.solve();
        int[] matching = sm.getSolution();
        // System.out.println("Expect:");
        // LLP.parr(expected);
        // System.out.println("Got:");
        // LLP.parr(matching);

        assertTrue(isStableMatching(wprefs, mprefs, matching));
        // assertArrayEquals(expected, matching);
    }

    @Test
    public void testParallelReduce() {
        int[] A = { 1, 2, 3, 4 };
        int[] expected = { 10, 3, 7 };

        ParallelReduce pr = new ParallelReduce(A);
        pr.solve();
        int[] reduce = pr.getSolution();
        assertArrayEquals(expected, reduce);
    }

    @Test
    public void testParallelPrefix() {
        int[] A = { 1, 2, 3, 4, 5, 6, 7, 8 };
        int[] expected = { 0, 1, 3, 6, 10, 15, 21, 28 };

        ParallelReduce pr = new ParallelReduce(A);
        pr.solve();
        int[] S = pr.getSolution();

        ParallelPrefix pp = new ParallelPrefix(A, S);
        pp.solve();
        int[] prefix = pp.getSolution();
        assertArrayEquals(expected, prefix);
    }

    @Test
    public void testListRanking() {
        int[] parents = { 2, 4, 8, 8, -1, 2, 1, 6, 4 };
        int[] expected = { 3, 1, 2, 2, 0, 3, 2, 3, 1 };

        ListRank lr = new ListRank(parents);
        lr.solve();
        int[] distances = lr.getSolution();
        assertArrayEquals(expected, distances);
    }

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

import java.io.*;

/** Week 3 checks: sort, search, blocking rules and BFS. */
public class Week3Test {
    static int failed;

    /**
     * Print PASS or FAIL for one check and count the failures.
     */
    static void check(String what, boolean ok) {
        System.out.println((ok ? "PASS  " : "FAIL  ") + what);
        if (!ok) failed++;
    }

    /**
     * BFS meters from one place to another, or -1 when no path exists.
     */
    static int meters(String from, String to) {
        int s = DLSUMapFinder.find(from), t = DLSUMapFinder.find(to);
        int[] prev = new int[DLSUMapFinder.n];
        int[] dist = new int[DLSUMapFinder.n];
        return DLSUMapFinder.bfs(s, t, prev, dist) ? dist[t] : -1;
    }
}

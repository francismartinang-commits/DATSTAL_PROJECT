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

    public static void main(String[] args) {
        DLSUMapFinder.load("dlsu_map.txt");
        check("map loads (15 places)", DLSUMapFinder.n == 15);

        // ---- sort and linear search ----
        int[] o = DLSUMapFinder.order;
        boolean sorted = true;
        for (int i = 1; i < DLSUMapFinder.n; i++)
            sorted &= DLSUMapFinder.places[o[i - 1]].key
                    .compareTo(DLSUMapFinder.places[o[i]].key) <= 0;
        check("places are sorted A-Z", sorted);
        check("Agno Food Court comes first",
                DLSUMapFinder.places[o[0]].name.equals("Agno Food Court"));
        check("University Chapel comes last",
                DLSUMapFinder.places[o[DLSUMapFinder.n - 1]].name
                        .equals("University Chapel"));
        check("search finds a place whatever the capitals",
                DLSUMapFinder.find("hENRY sy HALL") >= 0);
        check("search gives -1 for an unknown place",
                DLSUMapFinder.find("Library") == -1);



    }
}

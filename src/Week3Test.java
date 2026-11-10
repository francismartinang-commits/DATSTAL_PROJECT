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

        // ---- blocking rules ----
        int lounge = DLSUMapFinder.find("Faculty Lounge (Restricted)");
        int menCR = DLSUMapFinder.find("Andrew CR (Men)");
        int womenCR = DLSUMapFinder.find("Andrew CR (Women)");
        DLSUMapFinder.gender = 'M';
        check("forbidden area is blocked", DLSUMapFinder.blocked(lounge));
        check("men may use the men's CR", !DLSUMapFinder.blocked(menCR));
        check("men may not use the women's CR",
                DLSUMapFinder.blocked(womenCR));
        DLSUMapFinder.gender = 'F';
        check("women may not use the men's CR", DLSUMapFinder.blocked(menCR));
        check("women may use the women's CR",
                !DLSUMapFinder.blocked(womenCR));

        // ---- BFS ----
        DLSUMapFinder.gender = 'M';
        check("men's route goes through the men's CR (22 m)",
                meters("Andrew Stairs", "Andrew Elevator") == 22);
        check("no path to the Rooftop Garden",
                meters("Henry Sy Hall", "Rooftop Garden") == -1);
        check("Faculty Lounge shortcut (65 m) is never used",
                meters("St. La Salle Hall", "Henry Sy Hall") > 65);
        DLSUMapFinder.gender = 'F';
        check("women's route avoids the men's CR (55 m)",
                meters("Andrew Stairs", "Andrew Elevator") == 55);
        check("one-stop route (Taft Gate to Andrew Hall = 90 m)",
                meters("Taft Gate", "Andrew Hall") == 90);

        System.out.println(failed == 0 ? "\nAll tests passed."
                : "\n" + failed + " test(s) FAILED.");
        System.exit(failed == 0 ? 0 : 1);
    }
}

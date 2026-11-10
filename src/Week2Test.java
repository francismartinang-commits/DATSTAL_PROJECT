import java.io.*;

/** Week 2 checks: map loader and graph. */
public class Week2Test {
    static int failed;

    /** Print PASS or FAIL for one check and count the failures. */
    static void check(String what, boolean ok) {
        System.out.println((ok ? "PASS  " : "FAIL  ") + what);
        if (!ok) failed++;
    }

    /** Meters of the edge from one place to another, or -1 if none. */
    static int weight(String from, String to) {
        int a = DLSUMapFinder.find(from), b = DLSUMapFinder.find(to);
        for (DLSUMapFinder.Edge e = DLSUMapFinder.adj[a]; e != null; e = e.next)
            if (e.to == b) return e.w;
        return -1;
    }

    public static void main(String[] args) throws IOException {
        // ---- loader ----
        DLSUMapFinder.load("dlsu_map.txt");
        check("15 places loaded", DLSUMapFinder.n == 15);
        check("21 pathways loaded", DLSUMapFinder.paths == 21);
        check("file order is kept (first place is Taft Gate)",
                DLSUMapFinder.places[0].name.equals("Taft Gate"));
        check("place type is read (Andrew Stairs is STAIRS)",
                DLSUMapFinder.places[DLSUMapFinder.find("Andrew Stairs")]
                        .type == 1);
        check("find ignores case and extra spaces",
                DLSUMapFinder.find("  TAFT   gate ") == 0);
        check("find gives -1 for an unknown place",
                DLSUMapFinder.find("Library") == -1);

        // ---- graph ----
        int degrees = 0;
        for (int i = 0; i < DLSUMapFinder.n; i++)
            for (DLSUMapFinder.Edge e = DLSUMapFinder.adj[i]; e != null;
                 e = e.next) degrees++;
        check("every pathway is stored both ways",
                degrees == 2 * DLSUMapFinder.paths);
        check("meters are kept (Taft Gate to St. La Salle Hall = 60)",
                weight("Taft Gate", "St. La Salle Hall") == 60);
        check("the reverse direction has the same meters",
                weight("St. La Salle Hall", "Taft Gate") == 60);
        check("places without a pathway are not joined",
                weight("Taft Gate", "Gokongwei Hall") == -1);
        DLSUMapFinder.printGraph();            // also look at this by eye
        System.out.println();

        // ---- error handling ----
        DLSUMapFinder.load("no_such_file.txt");
        check("missing file keeps the old map", DLSUMapFinder.n == 15);
        try (PrintWriter w = new PrintWriter(new FileWriter("bad_map.txt"))) {
            w.println("PLACE,A,PLACE");
            w.println("PLACE,A,PLACE");           // duplicate place
            w.println("PLACE,B,SPACESHIP");       // unknown type
            w.println("PLACE,B,STAIRS");
            w.println("PATH,A,B,abc");            // bad meters
            w.println("PATH,A,Z,5");              // unknown place
            w.println("PATH,A,B,5");
            w.println("PATH,B,A,5");              // duplicate pathway
            w.println("HELLO");                   // unknown record
        }
        DLSUMapFinder.load("bad_map.txt");
        check("bad lines are skipped, good ones kept",
                DLSUMapFinder.n == 2 && DLSUMapFinder.paths == 1);
        new File("bad_map.txt").delete();

        System.out.println(failed == 0 ? "\nAll tests passed."
                : "\n" + failed + " test(s) FAILED.");
        System.exit(failed == 0 ? 0 : 1);
    }
}
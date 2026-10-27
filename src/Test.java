public class Test {
    static int failed;


    static void check(String what, boolean ok) {
        System.out.println((ok ? "PASS  " : "FAIL  ") + what);
        if (!ok) failed++;
    }


    public static void main(String[] args) {
        int max = DLSUMapFinder.MAX;
        DLSUMapFinder.Place[] p = DLSUMapFinder.places;


        // ---- the array of places ----
        int a = DLSUMapFinder.addPlace("Henry Sy Hall", 0);int b = DLSUMapFinder.addPlace("  Andrew   STAIRS ", 1);
        check("ids are handed out as 0, 1", a == 0 && b == 1);
        check("two places are stored", DLSUMapFinder.n == 2);
        check("name is kept as given", p[0].name.equals("Henry Sy Hall"));
        check("key is cleaned up", p[1].key.equals("andrew stairs"));
        check("type is kept", p[1].type == 1);
        while (DLSUMapFinder.n < max) DLSUMapFinder.addPlace("Room", 0);
        check("array fills up to MAX", DLSUMapFinder.n == max);
        check("adding to a full array gives -1",
                DLSUMapFinder.addPlace("Extra", 0) == -1);


        // ---- the queue ----
        DLSUMapFinder.Queue q = new DLSUMapFinder.Queue();
        check("new queue is empty", q.isEmpty());
        check("poll on an empty queue gives -1", q.poll() == -1);
        q.add(5);
        q.add(7);
        q.add(9);
        check("first in, first out",
                q.poll() == 5 && q.poll() == 7 && q.poll() == 9);
        boolean ok = true;                     // slide 2 items through the
        q.add(0);
        q.add(1);                    // array 3 times to force a wrap
        for (int i = 2; i < 3 * max; i++) {
            q.add(i);
            ok &= q.poll() == i - 2;
        }
        check("order stays right after wrapping around", ok && q.size == 2);
        DLSUMapFinder.Queue f = new DLSUMapFinder.Queue();
        boolean filled = true;
        for (int i = 0; i < max; i++) filled &= f.add(i);
        check("queue accepts MAX items", filled);
        check("adding to a full queue gives false", !f.add(999));


        // ---- together, the way BFS will use them: ids in, names out ----
        DLSUMapFinder.n = 0;
        DLSUMapFinder.Queue d = new DLSUMapFinder.Queue();
        String[] names = {"Taft Gate", "St. La Salle Hall", "Andrew Hall"};
        for (String s : names) d.add(DLSUMapFinder.addPlace(s, 0));
        System.out.print("\nVisit order:");
        while (!d.isEmpty())
            System.out.print("  " + DLSUMapFinder.places[d.poll()].name);
        System.out.println("\n");


        System.out.println(failed == 0 ? "All tests passed."
                : failed + " test(s) FAILED.");
        System.exit(failed == 0 ? 0 : 1);
    }
}


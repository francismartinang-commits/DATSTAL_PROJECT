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




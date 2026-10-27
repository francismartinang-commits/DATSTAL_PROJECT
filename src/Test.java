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
        int a = DLSUMapFinder.addPlace("Henry Sy Hall", 0);

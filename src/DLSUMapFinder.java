public class DLSUMapFinder {
    static final int MAX = 100;                // most places one map may hold
    static final String[] TYPES =              // Place.type indexes this list
            {"PLACE", "STAIRS", "ELEVATOR", "CR_M", "CR_F", "FORBIDDEN"};
    static final int CR_M = 3, CR_F = 4, FORBIDDEN = 5;

    /* ---------------------- data structures---------------- */

    /** One campus location: an element of the places array. */
    static class Place {
        String name, key;                      // key = normalized name
        int type;                              // index into TYPES
        Place(String name, int type) {
            this.name = name; this.type = type; key = norm(name);
        }
    }

    static Place[] places = new Place[MAX];    // the array of places
    static int n;                              // how many places are stored

    // Trim, squeeze spaces, lower-case: makes name matching forgiving.
    static String norm(String s) {
        return s.trim().replaceAll("\\s+", " ").toLowerCase();
    }

    
}

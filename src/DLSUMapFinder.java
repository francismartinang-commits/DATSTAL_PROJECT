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

    static class Queue {
        int[] a = new int[MAX]; int head, size;
        boolean add(int v) {                   // false when the queue is full
            if (size == MAX) return false;
            a[(head + size++) % MAX] = v;      // next free slot, wraps around
            return true;
        }
        int poll() {                           // -1 when the queue is empty
            if (size == 0) return -1;
            int v = a[head];
            head = (head + 1) % MAX; size--;
            return v;
        }
        boolean isEmpty() { return size == 0; }
    }


    static Place[] places = new Place[MAX];    // the array of places
    static int n;                              // how many places are stored

    // Trim, squeeze spaces, lower-case: makes name matching forgiving.
    static String norm(String s) {
        return s.trim().replaceAll("\\s+", " ").toLowerCase();
    }

    // Add a place at the end of the array: returns its id, or -1 if full.
    static int addPlace(String name, int type) {
        if (n == MAX) return -1;
        places[n] = new Place(name, type);
        return n++;
    }

    public static void main(String[] args) {
        System.out.println("Week 1 build. To test it run: java Week 1 Test");
    }
}

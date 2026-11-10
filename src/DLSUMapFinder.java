
import java.io.*;

public class DLSUMapFinder {
    static final int MAX = 100;                // most places one map may hold
    static final String[] TYPES =              // Place.type indexes this list
            {"PLACE", "STAIRS", "ELEVATOR", "CR_M", "CR_F", "FORBIDDEN"};
    static final int CR_M = 3, CR_F = 4, FORBIDDEN = 5;

    /* ---------------------- data structures---------------- */

    /**
     * One campus location: an element of the places array.
     */
    static class Place {
        String name, key;                      // key = normalized name
        int type;                              // index into TYPES

        Place(String name, int type) {
            this.name = name;
            this.type = type;
            key = norm(name);
        }
    }

    static class Queue {
        int[] a = new int[MAX];
        int head, size;

        boolean add(int v) {                   // false when the queue is full
            if (size == MAX) return false;
            a[(head + size++) % MAX] = v;      // next free slot, wraps around
            return true;
        }

        int poll() {                           // -1 when the queue is empty
            if (size == 0) return -1;
            int v = a[head];
            head = (head + 1) % MAX;
            size--;
            return v;
        }

        boolean isEmpty() {
            return size == 0;
        }
    }


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

    static class Edge {
        int to, w;
        Edge next;                  // target id, meters, next edge

        Edge(int to, int w, Edge next) {
            this.to = to;
            this.w = w;
            this.next = next;
        }
    }

    static Place[] places = new Place[MAX];    // the array of places
    static Edge[] adj = new Edge[MAX];         // graph: one list per place
    static int n, paths;                       // places and pathways stored

    /**
     * Look a place up by name (ignores case and spaces): id, or -1.
     */
    static int find(String name) {
        String k = norm(name);
        for (int i = 0; i < n; i++)
            if (places[i].key.equals(k)) return i;
        return -1;
    }


    /**
     * Is there already a pathway between places a and b?
     */
    static boolean hasPath(int a, int b) {
        for (Edge e = adj[a]; e != null; e = e.next)
            if (e.to == b) return true;
        return false;
    }


    /**
     * Store a two-way pathway: one edge in each direction.
     */
    static void addPath(int a, int b, int w) {
        adj[a] = new Edge(b, w, adj[a]);
        adj[b] = new Edge(a, w, adj[b]);
        paths++;
    }


    /**
     * Handle one split line: returns an error text, or null if accepted.
     */
    static String parse(String[] f) {
        if (f[0].equalsIgnoreCase("PLACE")) {      // PLACE,name,TYPE
            if (f.length != 3 || f[1].isEmpty()) return "use PLACE,name,TYPE";
            int t = 0;
            while (t < TYPES.length && !TYPES[t].equalsIgnoreCase(f[2])) t++;
            if (t == TYPES.length) return "unknown type '" + f[2] + "'";
            if (n == MAX) return "too many places (limit " + MAX + ")";
            if (find(f[1]) >= 0) return "duplicate place '" + f[1] + "'";
            addPlace(f[1], t);
            return null;
        }
        if (f[0].equalsIgnoreCase("PATH")) {       // PATH,placeA,placeB,meters
            if (f.length != 4) return "use PATH,placeA,placeB,meters";
            int a = find(f[1]), b = find(f[2]), w = 0;
            if (a < 0 || b < 0) return "unknown place in pathway";
            if (a == b) return "a pathway must join two different places";
            if (hasPath(a, b)) return "this pathway is already in the map";
            try {
                w = Integer.parseInt(f[3]);
            } catch (NumberFormatException e) { /* w stays 0 -> rejected */ }
            if (w < 1 || w > 100000)
                return "meters must be a whole number, 1 to 100000";
            addPath(a, b, w);
            return null;
        }
        return "unknown record '" + f[0] + "'";
    }
}

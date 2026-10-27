static class Queue {
    int[] a = new int[MAX]; int head, size;
    boolean add(int v) {                   // false when the queue is full
        if (size == MAX) return false;
        a[(head + size++) % MAX] = v;      // next free slot, wraps around
        return true;
    }
    int poll() {                           // -1 when the queue is empty
        if (size == 0) return -1;

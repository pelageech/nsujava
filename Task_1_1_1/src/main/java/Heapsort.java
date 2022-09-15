import java.util.Collections;
import java.util.List;

/**
 * Heapsort is a O(NlogN)-sort based on array (heap).
 * Array becomes a binary heap:
 *
 *          0 (item)
 *         /        \
 *      1 (item)   2 (item)
 *      /      \
 *   3 (item)  4 (item)
 *
 * and so on.
 */
public class Heapsort {

/**
 * @param arr start Array
 * @return Sorted Array
 */
public static List<Integer> heapsort(List<Integer> arr) {
    int arrsize = arr.size();

    for (int i = arrsize - 1; i >= 0; i--) {
        heapify(arr, arrsize, i);
    }

    for (int i = arrsize - 1; i > 0; i--) {
        Collections.swap(arr, i, 0);
        heapify(arr, i, 0);
    }
    return arr;
}

/**
 * @param arr Array (binary heap)
 * @param n Conditional size of arr
 * @param i Root of (sub-)tree
 */
private static void heapify(List<Integer> arr, int n, int i) {
    int large = i;
    int l = 2 * i + 1;
    int r = l + 1;

    if (l < n && arr.get(l) > arr.get(large)) {
        large = l;
    }
    if (r < n && arr.get(r) > arr.get(large)) {
        large = r;
    }

    if (i != large) {
        Collections.swap(arr, i, large);
        heapify(arr, n, large);
    }
}

}

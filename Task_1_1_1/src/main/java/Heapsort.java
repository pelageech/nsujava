import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;

public class Heapsort {

    public static List<Integer> heapsort(List<Integer> arr) {
        int arr_size = arr.size();

        for (int i = arr_size - 1; i >= 0; i--) {
            heapify(arr, arr_size, i);
        }

        for (int i = arr_size - 1; i > 0; i--) {
            Collections.swap(arr, i, 0);
            heapify(arr, i, 0);
        }
        return arr;
    }

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

package ru.nsu.ablaginin;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ru.nsu.ablaginin.Heapsort is a O(NlogN)-sort based on heap.
 * Array or list becomes a binary heap:
 *          0 (item)
 *         /        \
 *      1 (item)   2 (item)
 *      /      \
 *   3 (item)  4 (item)
 *                              and so on.
 */
public class Heapsort {

  /**
   * heapsort() - main func.
   *
   * @param integerList input List
   * @return Sorted integerList
   */
  public static List<Integer> heapsort(List<Integer> integerList) {
    int listSize = integerList.size();

    for (int i = listSize - 1; i >= 0; i--) {
      heapify(integerList, listSize, i);
    }

    for (int i = listSize - 1; i > 0; i--) {
      Collections.swap(integerList, i, 0);
      heapify(integerList, i, 0);
    }
    return integerList;
  }

  /**
   * heapify means that for each subtree root is the largest.
   *
   * @param integerList Binary heap
   * @param n Conditional size of integerList
   * @param i Root of (sub-)tree
   */
  private static void heapify(List<Integer> integerList, int n, int i) {
    int large = i;
    int l = 2 * i + 1;
    int r = l + 1;

    if (l < n && integerList.get(l) > integerList.get(large)) {
      large = l;
    }
    if (r < n && integerList.get(r) > integerList.get(large)) {
      large = r;
    }

    if (i != large) {
      Collections.swap(integerList, i, large);
      heapify(integerList, n, large);
    }
  }
}

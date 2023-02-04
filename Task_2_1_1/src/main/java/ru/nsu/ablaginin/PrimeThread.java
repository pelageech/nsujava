package ru.nsu.ablaginin;

import java.util.Arrays;

public class PrimeThread {

  public static boolean parallelContainsNotPrime(int[] arr, int cores) {
    // algorithm starts here
    int len = arr.length;
    int threadLen = len / cores;
    int remains = len % cores;
    int from;
    int to = 0;

    // separate an array into `cores` parts
    int[][] subArrays = new int[cores][];
    for (int i = 0; i < cores; i++) {
      from = to;
      to = (i + 1) * threadLen;
      if (remains > 0) {
        remains--;
        to++;
      }
      subArrays[i] = Arrays.copyOfRange(arr, from, to);
    }

    // init an array of threads
    Prime[] threads = new Prime[cores];

    // init tasks for all the threads
    for (int i = 0; i < cores; i++) {
      threads[i] = new Prime(subArrays[i]);
      threads[i].start();
    }

    // use `join` to wait for all the threads
    for (int i = 0; i < cores; i++) {
      try {
        threads[i].join();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }

    return Arrays.stream(threads).anyMatch(Prime::getResult);
  }
}

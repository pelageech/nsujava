package ru.nsu.ablaginin;

import java.util.Arrays;

public class PrimeThread {

  public static boolean parallelContainsNotPrime(int[] arr, int CORES) {
    // algorithm starts here
    int len = arr.length;
    int threadLen = len / CORES;
    int remains = len % CORES;
    int from;
    int to = 0;

    // separate an array into `CORES` parts
    int[][] subArrays = new int[CORES][];
    for (int i = 0; i < CORES; i++) {
      from = to;
      to = (i + 1) * threadLen;
      if (remains > 0) {
        remains--;
        to++;
      }
      subArrays[i] = Arrays.copyOfRange(arr, from, to);
    }

    // init an array of threads
    Prime[] threads = new Prime[CORES];

    // init tasks for all the threads
    for (int i = 0; i < CORES; i++) {
      threads[i] = new Prime(subArrays[i]);
      threads[i].start();
    }

    // use `join` to wait for all the threads
    for (int i = 0; i < CORES; i++) {
      try {
        threads[i].join();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }

    return Arrays.stream(threads).anyMatch(Prime::getResult);
  }
}

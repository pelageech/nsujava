package ru.nsu.ablaginin;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;
import static ru.nsu.ablaginin.Prime.CORES;

class PrimeTest {
  @Test
  public void singleThreadTest() {
    long tm_stp = System.nanoTime();
    try (InputStream is = getClass().getClassLoader().getResourceAsStream("1.in")) {
      assertNotNull(is);

      Scanner sc = new Scanner(is);

      int n = sc.nextInt();
      int[] arr = new int[n];

      for (int i = 0; i < n; i++) {
        arr[i] = sc.nextInt();
      }

      Prime prime = new Prime();
      assertFalse(prime.containsNotPrime(arr));
    } catch (IOException e) {
      fail();
    }
    System.out.println("Single thread: " + (System.nanoTime() - tm_stp) + " ns.");
  }

  @Test
  public void multiThreadTest() {
    long tm_stp = System.nanoTime();
    try (InputStream is = getClass().getClassLoader().getResourceAsStream("1.in")) {
      assertNotNull(is);

      Scanner sc = new Scanner(is);

      int n = sc.nextInt();
      int[] arr = new int[n];

      for (int i = 0; i < n; i++) {
        arr[i] = sc.nextInt();
      }
      Prime prime = new Prime();

      // algorithm starts here
      int len = arr.length;
      int threadLen = len / CORES;
      int remains = len % CORES;
      int from;
      int to = 0;

      AtomicBoolean result = new AtomicBoolean(false);

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
      Thread[] threads = new Thread[CORES];

      // init tasks for all the threads
      for (int i = 0; i < CORES; i++) {
        int finalI = i;
        Runnable task = () -> {
          boolean b = prime.containsNotPrime(subArrays[finalI]);
          if (b) {
            result.set(true);
          }
        };
        Thread th = new Thread(task);
        threads[i] = th;
        th.start();
      }

      // use `join` to wait for all the threads
      for (int i = 0; i < CORES; i++) {
        try {
          threads[i].join();
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }

      assertFalse(result.get());
    } catch (IOException e) {
      fail();
    }
    System.out.println("Multi thread: " + (System.nanoTime() - tm_stp) + " ns.");
  }

  @Test
  public void parallelStreamTest() {
    long tm_stp = System.nanoTime();
    try (InputStream is = getClass().getClassLoader().getResourceAsStream("1.in")) {
      assertNotNull(is);

      Scanner sc = new Scanner(is);

      int n = sc.nextInt();
      int[] arr = new int[n];

      for (int i = 0; i < n; i++) {
        arr[i] = sc.nextInt();
      }
      Prime prime = new Prime();
      assertFalse(Arrays.stream(arr).parallel().anyMatch(prime::isNotPrime));
    } catch (IOException e) {
      fail();
    }

    System.out.println("Parallel stream: " + (System.nanoTime() - tm_stp) + " ns.");
  }
}
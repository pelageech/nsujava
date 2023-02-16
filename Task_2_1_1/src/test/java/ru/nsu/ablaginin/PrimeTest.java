package ru.nsu.ablaginin;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import org.junit.jupiter.api.Test;

class PrimeTest {
  public static final int CORES = 8;

  @Test
  public void singleThreadTest() {
    long timestamp = System.nanoTime();
    try (InputStream is = getClass().getClassLoader().getResourceAsStream("1.in")) {
      assertNotNull(is);

      Scanner sc = new Scanner(is);

      int n = sc.nextInt();
      int[] arr = new int[n];

      for (int i = 0; i < n; i++) {
        arr[i] = sc.nextInt();
      }

      Prime prime = new Prime(arr);
      assertFalse(prime.containsNotPrime());
    } catch (Exception e) {
      fail();
    }
    System.out.println("Single thread: " + (System.nanoTime() - timestamp) + " ns.");
  }

  @Test
  public void multiThreadTest() {
    long timestamp = System.nanoTime();
    try (InputStream is = getClass().getClassLoader().getResourceAsStream("1.in")) {
      assertNotNull(is);

      Scanner sc = new Scanner(is);

      int n = sc.nextInt();
      int[] arr = new int[n];

      for (int i = 0; i < n; i++) {
        arr[i] = sc.nextInt();
      }

      assertFalse(PrimeThread.parallelContainsNotPrime(arr, CORES));
    } catch (Exception e) {
      fail();
    }
    System.out.println("Multi thread: " + (System.nanoTime() - timestamp) + " ns.");
  }

  @Test
  public void parallelStreamTest() {
    long timestamp = System.nanoTime();
    try (InputStream is = getClass().getClassLoader().getResourceAsStream("1.in")) {
      assertNotNull(is);

      Scanner sc = new Scanner(is);

      int n = sc.nextInt();
      int[] arr = new int[n];

      for (int i = 0; i < n; i++) {
        arr[i] = sc.nextInt();
      }
      ForkJoinPool myPool = new ForkJoinPool(CORES);
      boolean b = myPool.submit(
          () -> Arrays.stream(arr).parallel().anyMatch(Prime::isNotPrime)
      ).get();
      assertFalse(b);
    } catch (Exception e) {
      fail();
    }

    System.out.println("Parallel stream: " + (System.nanoTime() - timestamp) + " ns.");
  }
}
package ru.nsu.ablaginin;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrimeTest {
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
    } catch (IOException e) {
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

      assertFalse(PrimeThread.parallelContainsNotPrime(arr, 8));
    } catch (IOException e) {
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
      assertFalse(Arrays.stream(arr).parallel().anyMatch(Prime::isNotPrime));
    } catch (IOException e) {
      fail();
    }

    System.out.println("Parallel stream: " + (System.nanoTime() - timestamp) + " ns.");
  }
}
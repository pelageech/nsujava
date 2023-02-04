package ru.nsu.ablaginin;

import java.util.Arrays;

/**
 * Class Prime contains functions for searching
 * prime numbers. The function isNotPrime returns true if
 * the number IS NOT prime.
 * The function containsNotPrime returns true if the input
 * array contains NOT prime number.
 */
public class Prime extends Thread {

  private boolean result = false;
  private final int[] array;

  public static final int CORES = 6;

  /**
   * Constructs a new object. Use it if you want to
   * use `containsNotPrime`.
   *
   * @param array input array
   */
  public Prime(int[] array) {
    this.array = Arrays.copyOf(array, array.length);
  }

  /**
   * Returns true if the number is not prime.
   *
   * @param a an input number
   * @return true if the number is not prime
   */
  public static boolean isNotPrime(int a) {
    if (a < 2)  {
      throw new IllegalArgumentException("Number must be >= 2");
    }
    boolean b = false;
    for (int i = 2; i*i <= a; i++) {
      if (a % i == 0) {
        b = true;
        break;
      }
    }
    return b;
  }

  /**
   * Returns true if the array contains at least one non-prime
   * number.
   *
   * @return boolean
   */
  public boolean containsNotPrime() {
    return Arrays.stream(array).anyMatch(Prime::isNotPrime);
  }

  public boolean getResult() {
    return result;
  }

  @Override
  public void run() {
    result = containsNotPrime();
  }
}

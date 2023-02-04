package ru.nsu.ablaginin;

import java.util.Arrays;

public class Prime extends Thread {

  private boolean result = false;
  private final int[] array;

  public static final int CORES = 6;

  public Prime(int[] array) {
    this.array = Arrays.copyOf(array, array.length);
  }

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

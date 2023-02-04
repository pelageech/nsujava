package ru.nsu.ablaginin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Prime {
  public static final int CORES = 6;

  public boolean isNotPrime(int a) {
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

  public boolean containsNotPrime(int[] arr) {
    return Arrays.stream(arr).anyMatch(this::isNotPrime);
  }
}

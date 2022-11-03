package ru.nsu.ablaginin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Substring {
  private final String string;
  private long currentHash; // hash of substring in the string

  private static final long _MOD = ~(1 << 31); // prime number for `mod`
  private final long x; // factor in border [2.._MOD-1]
  private long xFactor; // x^(substrLen - 1)

  public Substring(String inputString) {
    string = inputString;

    x = generateX();
    xFactor = 0;
  }

  // Rabin-Cap algorithm

  public List<Integer> algorithmRabinKarp(String substring) {
    int beg = 0;
    int end = substring.length();

    xFactor = powMod(x, end - 1);
    currentHash = hash(string, end);
    long substringHash = hash(substring, end);

    int strLen = string.length();

    List<Integer> pointerArray = new ArrayList<>();
    while (true) {
      if (
          currentHash == substringHash
       && substring.equals(string.substring(beg, end))
      ) {
        pointerArray.add(beg);
      }

      if (end == strLen) {
        break;
      }

      nextHash(string.charAt(beg++), string.charAt(end++));
    }

    return pointerArray;
  }

  // module arithmetic

  private long addMod(long a, long b) {
    return ((a + b) % _MOD);
  }

  private long mulMod(long a, long b) {
    return ((a * b) % _MOD);
  }

  private long subMod(long a, long b) {
    return ((a - b) + _MOD) % _MOD;
  }

  private long powMod(long x, long y) { // fast pow % _MOD, O(logN)
    long res = 1;

    while (y > 0) {
      if (y % 2 == 1) {
        res = mulMod(res, x);
      }

      y >>= 1;
      x = mulMod(x, x);
    }

    return res;
  }

  // hash func
  private long hash(String str, int to) {
    long hash = 0;
    long factor = 1;

    for (int i = to - 1; i >= 0; i--) {
      long temp = mulMod(str.charAt(i), factor);
      hash = addMod(hash, temp);
      factor = mulMod(factor, x);
    }

    return hash;
  }

  // get next hash by formula: hash(s[i+1..m+i+1]) =
  // = ( hash(s[i..m+i]) - s[i] * x^(m - 1) ) * x + s[m+i+1]
  private void nextHash(char prevChar, char newChar) {
    long atom1 = mulMod(prevChar, xFactor);
    currentHash = subMod(currentHash, atom1);
    currentHash = mulMod(currentHash, x);
    currentHash = addMod(currentHash, newChar);
  }

  private long generateX() {
    Random rand = new Random();
    return rand.nextInt(2, (int)_MOD);
  }
}

package ru.nsu.ablaginin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class Substring defines an algorithm of
 * searching substring in the string.
 * The Rabin-Carp's algorithm was used for these aims.
 */
public class Substring {
  private final String string;
  private long currentHash; // hash of substring in the string

  private static final long _MOD = ~(1 << 31); // prime number for `mod`
  private final long magic; // factor in border [2.._MOD-1]
  private long magicFactor; // x^(substrLen - 1)

  /**
   * The constructor creates an object
   * that receives an inputString.
   * Also, it generates magic number in a range [2.._MOD - 1].
   *
   * @param inputString fixed string
   */
  public Substring(String inputString) {
    string = inputString;

    magic = generateMagic();
  }

  /**
   * Rabin-Carp's algorithm uses polynomial hash for searching.
   * For fixed prime number `_MOD` and random `magic` the hash
   * of string(s[1..N]) is sum(s[i] * magic^(N-i)) i (- [1..N].
   * So, if we compare hashes, and they're different, that means
   * the substrings are different. But there are probability of
   * coliseum, it's probability less than 1 / N^(c-2), c is more than 2,
   * and _MOD is more than N^c. It's EXTREMELY little.
   * So, we don't want to rehash a new substring in the string,
   * we should use the formula: hash(s[i+1..m+i+1]) =
   * = ( hash(s[i..m+i]) - s[i] * x^(m - 1) ) * x + s[m+i+1].
   * @param substring substring that's supposed to be found
   * @return list of pointers
   */
  public List<Integer> algorithmRabinKarp(String substring) {
    if (string == null || substring == null) {
      return null;
    }
    List<Integer> pointerArray = new ArrayList<>();

    int beg = 0;
    int end = substring.length();

    int strLen = string.length();
    if (end > strLen) {
      return pointerArray;
    }

    magicFactor = powMod(magic, end - 1);
    currentHash = hash(string, end);
    long substringHash = hash(substring, end);

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
    return fastMod(a + b);
  }

  private long mulMod(long a, long b) {
    return fastMod(a * b);
  }

  private long subMod(long a, long b) {
    return fastMod((a - b) + _MOD);
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

  private long fastMod(long value) {
    value = (value >> 31) + (value & _MOD);
    value = (value >> 31) + (value & _MOD);
    return value == _MOD ? 0 : value;
  }

  // hash func
  private long hash(String str, int to) {
    long hash = 0;
    long factor = 1;

    for (int i = to - 1; i >= 0; i--) {
      long temp = mulMod(str.charAt(i), factor);
      hash = addMod(hash, temp);
      factor = mulMod(factor, magic);
    }

    return hash;
  }

  // get next hash by formula: hash(s[i+1..m+i+1]) =
  // = ( hash(s[i..m+i]) - s[i] * x^(m - 1) ) * x + s[m+i+1]
  private void nextHash(char prevChar, char newChar) {
    long atom1 = mulMod(prevChar, magicFactor);
    currentHash = subMod(currentHash, atom1);
    currentHash = mulMod(currentHash, magic);
    currentHash = addMod(currentHash, newChar);
  }

  private long generateMagic() {
    Random rand = new Random();
    return rand.nextInt(2, (int) _MOD);
  }
}

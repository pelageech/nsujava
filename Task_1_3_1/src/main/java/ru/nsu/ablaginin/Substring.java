package ru.nsu.ablaginin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Class Substring defines an algorithm of
 * searching substring in the file.
 * It takes an item byte-by-byte and tries to search
 * a coincidence.
 * The Rabin-Carp's algorithm was used for these aims.
 */
public class Substring {
  private final InputStream inputStream;

  private final StringBuilder currentString; // substring in the string
  private long currentHash; // hash of substring in the string

  private static final long _MOD = ~(1 << 31); // prime number for `mod`
  private final long magic; // factor in border [2.._MOD-1]
  private long magicFactor; // x^(substrLen - 1)

  /**
   * The constructor creates an object
   * that receives an inputStream.
   * Also, it generates magic number in a range [2.._MOD - 1].
   *
   * @param inputStream fixed string
   */
  public Substring(InputStream inputStream) {
    this.inputStream = inputStream;
    currentString = new StringBuilder();
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
   *
   * @param substring substring that's supposed to be found
   * @return list of pointers
   */
  public List<LinePointers> algorithmRabinKarp(String substring) {
    // null check
    if (inputStream == null || substring == null || substring.equals("")) {
      return null;
    }

    try {
      // prepare reader
      Reader reader = new BufferedReader(new InputStreamReader(inputStream));

      // result list of pointers for each line
      List<LinePointers> pointerList = new ArrayList<>();

      // algorithm config
      int subLength = substring.length();
      long substringHash = hash(substring, subLength);
      magicFactor = powMod(magic, subLength - 1);

      int prevChar; // char that will be deleted
      int nextChar; // char that will be added

      // get first `subLength` chars
      for (int i = 0; i < subLength; i++) {
        nextChar = reader.read();
        if (nextChar == -1) {
          return null;
        }
        currentString.append((char) nextChar);
      }
      // count first hash
      currentHash = hash(currentString.toString(), subLength);

      prevChar = currentString.charAt(0);
      int currentLine = 1; // line in which we're searching
      int lineOffset = subLength - 1; // offset in the line

      // algorithm itself
      while (true) {

        // compare hashes and substrings
        if (
            currentHash == substringHash
                && substring.equals(currentString.toString())
        ) {
          LinePointers temp = pointerList.size() - 1 >= 0
              ? pointerList.get(pointerList.size() - 1)
              : null;

          if (temp == null || temp.line() != currentLine) {
            pointerList.add(new LinePointers(currentLine, new ArrayList<>()));
            temp = pointerList.get(pointerList.size() - 1);
          }

          temp.pointers().add(lineOffset - subLength + 1);
        }

        // read next char
        nextChar = reader.read();
        if (nextChar == -1) {
          break;
        }
        lineOffset++; // increase the offset

        // increase currentLine on `\n`
        if (nextChar == '\n') {
          currentLine++;
          lineOffset = -1; // we aren't in the beginning of the next line
        }
        // count new hash
        nextHash(prevChar, nextChar);

        // get the next substring
        currentString.deleteCharAt(0);
        currentString.append((char) nextChar);

        prevChar = currentString.charAt(0); // change prevChar
      }

      return pointerList;
    } catch (IOException e) {
      return null;
    }
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
  private void nextHash(int prevChar, int newChar) {
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

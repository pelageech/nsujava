package ru.nsu.ablaginin;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * FileSubstring contains InputStream for searching
 * a substring in a whole text
 */
public class FileSubstring {
  private final InputStream is;

  /**
   * The constructor creates an object with a fixed
   * inputStream parameter.
   *
   * @param is inputStream
   */
  public FileSubstring(InputStream is) {
    this.is = is;
  }

  /**
   * For each line we use Rabin-Carp's algorithm.
   *
   * @param substring substring that's supposed to be found
   * @return a list of a lists with pointers for each line
   */
  public List<LinePointers> findSubstring(String substring) {
    Scanner sc;
    if (is != null) {
      sc = new Scanner(is);
    } else {
      return null;
    }

    List<LinePointers> substringPointersList = new ArrayList<>();

    int curLine = 1;
    while (sc.hasNextLine()) {
      String current = sc.nextLine();
      Substring sub = new Substring(current);

      List<Integer> pointers = sub.algorithmRabinKarp(substring);
      if (pointers.size() > 0) {
        substringPointersList.add(new LinePointers(curLine, pointers));
      }
      curLine++;
    }

    return substringPointersList;
  }
}

package ru.nsu.ablaginin;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileSubstring {
  private final InputStream is;

  public FileSubstring(InputStream is) {
    this.is = is;
  }

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

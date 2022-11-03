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

  public List<List<Integer>> findSubstring(String substring) {
    Scanner sc;
    if (is != null) {
      sc = new Scanner(is);
    } else {
      return null;
    }

    List<List<Integer>> substringPointersList = new ArrayList<>();

    while (sc.hasNextLine()) {
      String current = sc.nextLine();
      Substring sub = new Substring(current);

      substringPointersList.add(sub.algorithmRabinKarp(substring));
    }

    return substringPointersList;
  }
}

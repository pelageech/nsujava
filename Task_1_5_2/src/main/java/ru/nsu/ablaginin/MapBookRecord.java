package ru.nsu.ablaginin;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Typedef for Map(String, BookRecord).
 */
public class MapBookRecord {
  private final Map<String, BookRecord> map = new LinkedHashMap<>();

  public Map<String, BookRecord> getMap() {
    return map;
  }
}

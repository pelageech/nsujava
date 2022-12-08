package ru.nsu.ablaginin;

import java.util.HashMap;
import java.util.Map;

/**
 * Typedef of HashMap String, GradeBookRecord.
 */
public class SubjectToGrade {
  private final Map<String, GradeBookRecord> map = new HashMap<>();

  public Map<String, GradeBookRecord> getMap() {
    return map;
  }
}

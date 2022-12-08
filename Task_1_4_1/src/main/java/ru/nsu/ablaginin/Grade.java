package ru.nsu.ablaginin;

/**
 * Enum Grade for a grade book.
 */
public enum Grade {
  BAD,
  SATISFIED,
  GOOD,
  EXCELLENT;

  @Override
  public String toString() {
    return switch (this) {
      case BAD ->
        "2";
      case SATISFIED ->
        "3";
      case GOOD ->
        "4";
      case EXCELLENT ->
        "5";
    };
  }
}

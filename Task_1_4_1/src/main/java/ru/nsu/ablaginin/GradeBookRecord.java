package ru.nsu.ablaginin;

import java.util.Calendar;
import java.util.Optional;

/**
 * Record for the grade book.
 *
 * @param subject name of the subject
 * @param grade grade the student has got
 * @param teacher the teacher who gives a course
 * @param date the date of grade
 */
public record GradeBookRecord(String subject, Grade grade,
                              Optional<String> teacher,
                              Optional<Calendar> date) {
  /**
   * Checks not nulls.
   *
   * @param subject see above
   * @param grade see above
   * @param teacher see above
   * @param date see above
   */
  public GradeBookRecord {
    if (subject == null || grade == null) {
      throw new NullPointerException();
    }
  }
}

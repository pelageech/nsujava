package ru.nsu.ablaginin;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

/**
 * Record for the grade book.
 *
 * @param subject name of the subject
 * @param grade grade the student has got
 * @param teacher the teacher who gives a course
 * @param date the date of grade
 */
public record GradeBookRecord(@NotNull String subject, @NotNull Grade grade,
                              Optional<String> teacher,
                              Optional<Calendar> date) {
  @Override
  public String toString() {
    var teacherString = "";
    if (teacher.isPresent()) {
      teacherString = teacher.get();
    }
    var format = new SimpleDateFormat("yyyy-MM-dd");
    var dateString = "";
    if (date.isPresent()) {
      dateString = format.format(date.get().getTime());
    }
    return "subject='" + subject + '\''
        + ", grade=" + grade
        + ", teacher=" + teacherString
        + ", date=" + dateString;
  }
}

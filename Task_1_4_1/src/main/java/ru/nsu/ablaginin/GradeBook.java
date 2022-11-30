package ru.nsu.ablaginin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

/**
 * Student's Grade Book helps students (really?!) save
 * their grades in one structure that lets every student
 * estimate chances to get increased scholarship and red diploma.
 * All the grades stores in a map, there are a few maps that have
 * a semester number. So, all the grades are contained in maps, all
 * the maps are in the list.
 */
public class GradeBook {
  private static final int MAX_SEMESTER = 99;

  private static long id_counter = 0;

  private int globalExcellentGrades = 0;
  private int globalGrades = 0;
  private int globalSatisfiedGrades = 0;

  // user fields
  private final String studentName;
  private final long studentId;
  private int currentSemester = 1;
  private Grade diplomaGrade = Grade.BAD;
  private boolean redDiploma = false;
  private final List<SubjectToGrade> gradeBook = new ArrayList<>();

  /**
   * The constructor creates an empty grade book
   * with the first semester.
   */
  public GradeBook(@NotNull String name) {
    studentName = name.concat("");
    studentId = id_counter++;

    gradeBook.add(new SubjectToGrade());
  }

  /**
   * Adds a new semester record in the grade book.
   *
   * @return true on successful adding a semester
   */
  public boolean newSemester() {
    if (currentSemester == MAX_SEMESTER
        || gradeBook.get(currentSemester - 1).getMap().size() == 0) {
      return false;
    }

    gradeBook.add(new SubjectToGrade());
    currentSemester++;
    return true;
  }

  /**
   * Adds a new grade in the grade book.
   * If the subject already has a grade, the method
   * replaces the current record with new one.
   * If semester is zero, the current semester is used.
   *
   * @param semester what semester adds
   * @param record all the fields in the gradeBook's row
   * @return true on successful adding a grade
   */
  public boolean putRecord(int semester, @NotNull GradeBookRecord record) {
    if (semester < 0 || semester > currentSemester) {
      throw new IllegalArgumentException();
    }

    int semesterIsUsed = semester == 0 ? currentSemester : semester;

    // get the semester
    Map<String, GradeBookRecord> currentPage = gradeBook.get(semesterIsUsed - 1).getMap();

    GradeBookRecord prevRecord = currentPage.put(record.subject(), record);
    if (prevRecord != null) {
      checkPreviousGrade(prevRecord.grade());
    }

    // grade counters update
    globalGrades++;

    globalExcellentGrades = record.grade() == Grade.EXCELLENT
        ? globalExcellentGrades + 1
        : globalExcellentGrades;

    globalSatisfiedGrades = record.grade() == Grade.SATISFIED
        ? globalSatisfiedGrades + 1
        : globalSatisfiedGrades;

    // check buns
    checkRedDiploma();
    return true;
  }

  /**
   * Removes a record from the book.
   * If a semester is null, the current semester is used.
   *
   * @param semester what semester remove from
   * @param subject name of the subject
   * @return true on successful removing
   */
  public boolean removeRecord(int semester, @NotNull String subject) {
    if (semester < 0 || semester > currentSemester) {
      throw new IllegalArgumentException();
    }
    int semesterIsUsed = semester == 0 ? currentSemester : semester;

    Map<String, GradeBookRecord> currentPage = gradeBook.get(semesterIsUsed - 1).getMap();
    GradeBookRecord record = currentPage.remove(subject);
    if (record == null) {
      return false;
    }
    globalGrades--;
    checkPreviousGrade(record.grade());

    redDiploma = diplomaGrade == Grade.EXCELLENT
        && 4 * globalExcellentGrades >= 3 * globalGrades
        && globalSatisfiedGrades == 0;
    return true;
  }

  /**
   * Checks if a student deserves increased scholarship.
   *
   * @return true if a student deserves increased scholarship
   */
  public boolean isIncreasedScholarship() {
    Map<String, GradeBookRecord> currentPage = gradeBook.get(currentSemester - 1).getMap();

    return currentPage.values().stream().allMatch(n -> n.grade() == Grade.EXCELLENT);
  }

  // Setters

  /**
   * Sets a diploma's grade.
   *
   * @param diplomaGrade grade to set
   */
  public void setDiplomaGrade(@NotNull Grade diplomaGrade) {
    this.diplomaGrade = diplomaGrade;
    redDiploma = diplomaGrade == Grade.EXCELLENT
        && 4 * globalExcellentGrades >= 3 * globalGrades
        && globalSatisfiedGrades == 0;
  }

  // Getters


  public String getStudentName() {
    return studentName;
  }

  public long getStudentId() {
    return studentId;
  }

  public List<SubjectToGrade> getGradeBook() {
    return gradeBook;
  }

  public Grade getDiplomaGrade() {
    return diplomaGrade;
  }

  public int getCurrentSemester() {
    return currentSemester;
  }

  public boolean isRedDiploma() {
    return redDiploma;
  }

  private void checkRedDiploma() {
    redDiploma = diplomaGrade == Grade.EXCELLENT
        && 4 * globalExcellentGrades >= 3 * globalGrades
        && globalSatisfiedGrades == 0;
  }

  private void checkPreviousGrade(Grade grade) {
    if (grade == Grade.SATISFIED) {
      globalSatisfiedGrades--;
    } else if (grade == Grade.EXCELLENT) {
      globalExcellentGrades--;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GradeBook book = (GradeBook) o;
    return studentId == book.studentId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(studentId);
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder("Name: ");
    result.append(this.studentName);

    for (int i = 1; i <= currentSemester; i++) {
      result.append("\n\n");
      var map = this.gradeBook.get(i - 1).getMap();
      if (map.size() == 0) {
        continue;
      }

      result.append("Semester ").append(i).append("\n");
      map.forEach((k, v) -> {
        result.append(v);
        result.append('\n');
      });
    }

    result.append("\nDiploma grade: ");
    if (diplomaGrade == Grade.BAD) {
      result.append("Not yet\n");
    } else {
      result.append(diplomaGrade).append('\n');
      result.append("Is red diploma: ").append(isRedDiploma()).append('\n');
    }

    if (this.gradeBook.get(currentSemester - 1).getMap().size() > 0) {
      result.append("Is increased scholarship: ").append(isIncreasedScholarship()).append('\n');
    }
    return result.toString();
  }

  public static void main(String[] args) {
    var book = new GradeBook("A B S");
    book.putRecord(0, new GradeBookRecord(
        "fjdsofj",
        Grade.EXCELLENT,
        Optional.empty(),
        Optional.of(new GregorianCalendar(2022, Calendar.MAY, 24))
    ));
    System.out.println(book);
  }
}

package ru.nsu.ablaginin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

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

  private int globalExcellentGrades;
  private int globalGrades;
  private int globalSatisfiedGrades;

  // user fields
  private int currentSemester;
  private int diplomaGrade;
  private boolean redDiploma;
  private final List<Map<String, GradeBookRecord>> gradeBook;

  /**
   * The constructor creates an empty grade book
   * with the first semester.
   */
  public GradeBook() {
    globalExcellentGrades = 0;
    globalGrades = 0;
    globalSatisfiedGrades = 0;

    currentSemester = 1;
    diplomaGrade = 0;
    redDiploma = false;
    gradeBook = new ArrayList<>();

    gradeBook.add(new HashMap<>());
  }

  /**
   * Adds a new semester record in the grade book.
   *
   * @return true on successful adding a semester
   */
  public boolean newSemester() {
    if (currentSemester == MAX_SEMESTER
        || gradeBook.get(currentSemester - 1).size() == 0) {
      return false;
    }

    gradeBook.add(new HashMap<>());
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
  public boolean putRecord(int semester, GradeBookRecord record) {
    if (record == null) {
      return false;
    }
    // check null fields in the record
    if (record.grade() == null || record.subject() == null) {
      return false;
    }

    int semesterIsUsed = semester == 0 ? currentSemester : semester;

    // correctness of grade
    if (record.grade() > 5 || record.grade() < 2) {
      return false;
    }

    // get the semester
    try {
      Map<String, GradeBookRecord> currentPage = gradeBook.get(semesterIsUsed - 1);

      GradeBookRecord prevRecord = currentPage.put(record.subject(), record);
      if (prevRecord != null) {
        checkPreviousGrade(prevRecord.grade());
      }

      // grade counters update
      globalExcellentGrades = record.grade() == 5
          ? globalExcellentGrades + 1
          : globalExcellentGrades;
      globalGrades++;

      // check buns
      checkRedDiploma(record.grade());
      return true;

    } catch (IndexOutOfBoundsException e) {
      return false;
    }
  }

  /**
   * Removes a record from the book.
   * If a semester is null, the current semester is used.
   *
   * @param semester what semester remove from
   * @param subject name of the subject
   * @return true on successful removing
   */
  public boolean removeRecord(int semester, String subject) {
    int semesterIsUsed = semester == 0 ? currentSemester : semester;

    try {
      Map<String, GradeBookRecord> currentPage = gradeBook.get(semesterIsUsed - 1);
      GradeBookRecord record = currentPage.remove(subject);
      if (record == null) {
        return false;
      }
      globalGrades--;
      if (record.grade() == 5) {
        globalExcellentGrades--;
      } else if (record.grade() == 3) {
        globalSatisfiedGrades--;
      }
      checkRedDiploma(0);
      return true;
    } catch (IndexOutOfBoundsException e) {
      return false;
    }
  }

  /**
   * Checks if a student deserves increased scholarship.
   *
   * @return true if a student deserves increased scholarship
   */
  public boolean isIncreasedScholarship() {
    Map<String, GradeBookRecord> currentPage = gradeBook.get(currentSemester - 1);

    AtomicBoolean result = new AtomicBoolean(true);
    currentPage.forEach((k, v) -> {
      if (v.grade() != 5) {
        result.set(false);
      }
    });

    return result.get();
  }

  // Setters

  /**
   * Sets a diploma's grade.
   *
   * @param diplomaGrade grade to set
   */
  public void setDiplomaGrade(int diplomaGrade) {
    if (diplomaGrade < 2 || diplomaGrade > 5) {
      return;
    }
    this.diplomaGrade = diplomaGrade;
    checkRedDiploma(0);
  }

  // Getters

  public List<Map<String, GradeBookRecord>> getGradeBook() {
    return gradeBook;
  }

  public int getDiplomaGrade() {
    return diplomaGrade;
  }

  public int getCurrentSemester() {
    return currentSemester;
  }

  public boolean isRedDiploma() {
    return redDiploma;
  }

  private void checkRedDiploma(int grade) {
    globalSatisfiedGrades = grade > 3 || grade == 0 ? globalSatisfiedGrades : globalSatisfiedGrades + 1;
    redDiploma = diplomaGrade == 5 && 4 * globalExcellentGrades >= 3 * globalGrades
        && globalSatisfiedGrades == 0;
  }

  private void checkPreviousGrade(int grade) {
    if (grade == 3) {
      globalSatisfiedGrades--;
    } else if (grade == 5) {
      globalExcellentGrades--;
    }
  }
}

package ru.nsu.ablaginin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  private int excellentCount;
  private int gradesCount;
  private boolean isThereSatisfiedGrade;

  // user fields
  private int currentSemester;
  private int diplomaGrade;
  private boolean redDiploma;
  private boolean increasedScholarship;
  private final List<Map<String, Integer>> gradeBook;

  /**
   * The constructor creates an empty grade book
   * with the first semester.
   */
  public GradeBook() {
    excellentCount = 0;
    gradesCount = 0;
    isThereSatisfiedGrade = false;

    currentSemester = 1;
    diplomaGrade = 0;
    redDiploma = false;
    increasedScholarship = true;
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

    increasedScholarship = true;
    gradeBook.add(new HashMap<>());
    currentSemester++;
    return true;
  }

  /**
   * Adds a new grade in the grade book.
   * If the subject already has a grade, you can
   * edit it.
   * If semester is zero, the current semester is used.
   *
   * @param semester what semester adds
   * @param subject what subject add
   * @param grade what's the grade
   * @return true on successful adding a grade
   */
  public boolean addGrade(int semester, String subject, int grade) {

    int semesterAdd = semester == 0 ? currentSemester : semester;

    // correctness of grade
    if (grade > 5 || grade < 2) {
      return false;
    }

    // get the semester
    Map<String, Integer> curr = gradeBook.get(semesterAdd - 1);
    if (curr == null) {
      return false;
    }
    // try to put the new value
    if (curr.put(subject, grade) == null) {
      return false;
    }

    // grade counters update
    excellentCount = grade == 5 ? excellentCount + 1 : excellentCount;
    gradesCount++;

    // check buns
    checkRedDiploma(grade);
    checkIncreasedScholarship(semesterAdd, grade);
    return true;
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
  }

  // Getters

  public List<Map<String, Integer>> getGradeBook() {
    return gradeBook;
  }

  public int getDiplomaGrade() {
    return diplomaGrade;
  }

  public int getCurrentSemester() {
    return currentSemester;
  }

  public boolean isIncreasedScholarship() {
    return increasedScholarship;
  }

  public boolean isRedDiploma() {
    return redDiploma;
  }

  private void checkRedDiploma(int grade) {
    isThereSatisfiedGrade = isThereSatisfiedGrade && grade > 3;
    redDiploma = 4 * excellentCount > 3 * gradesCount
        && !isThereSatisfiedGrade
        && diplomaGrade == 5;
  }

  private void checkIncreasedScholarship(int semester, int grade) {
    if (semester != currentSemester) {
      return;
    }
    if (grade != 5) {
      increasedScholarship = false;
    }
  }
}

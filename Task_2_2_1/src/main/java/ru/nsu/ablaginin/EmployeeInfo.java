package ru.nsu.ablaginin;

/**
 * Information about an employee.
 * There are name, age and gender.
 */
public class EmployeeInfo {
  private final String name;
  private final int age;
  private final String gender;

  /**
   * Constructor of EmployeeInfo.
   *
   * @param name employee's name
   * @param age employee's age
   * @param gender employee's gender
   */
  public EmployeeInfo(String name, int age, String gender) {
    this.name = name;
    this.age = age;
    this.gender = gender;
  }

  public String name() {
    return name;
  }

  public int age() {
    return age;
  }

  public String gender() {
    return gender;
  }
}
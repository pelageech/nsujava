package ru.nsu.ablaginin;

/**
 * Information about chefs. This class coincides with EmployeeInfo.
 */
public final class ChefInfo extends EmployeeInfo {
  /**
   * Info about a chef.
   *
   * @param name chef's name
   * @param age chef's age
   * @param gender chef's gender
   */
  public ChefInfo(String name, int age, String gender) {
    super(name, age, gender);
  }
}
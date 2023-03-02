package ru.nsu.ablaginin;

/**
 * Information about couriers. Besides, employee's info there is a capacity parameter -
 * how many pizza the courier can take with one's.
 */
public final class CourierInfo extends EmployeeInfo {

  private final int capacity;

  /**
   * Info about courier.
   *
   * @param name courier's name
   * @param age courier's age
   * @param gender courier's gender
   * @param capacity courier's capacity
   */
  public CourierInfo(String name, int age, String gender, int capacity) {
    super(name, age, gender);
    this.capacity = capacity;
  }

  public int capacity() {
    return capacity;
  }
}
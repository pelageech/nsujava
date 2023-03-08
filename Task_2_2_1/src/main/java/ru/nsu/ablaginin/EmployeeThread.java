package ru.nsu.ablaginin;

/**
 * Class is about employee in pizzeria.
 *
 * @param <T> info about employee
 */
public abstract class EmployeeThread<T extends EmployeeInfo> extends Thread {
  final Pizzeria pizzeria;
  final T info;

  public EmployeeThread(Pizzeria pizzeria, T employeeInfo) {
    this.pizzeria = pizzeria;
    info = employeeInfo;
  }

  public abstract void work() throws InterruptedException;

  @Override
  public void run() {
    while (true) {
      try {
        work();
      } catch (InterruptedException e) {
        System.out.println(e);
      }
    }
  }
}

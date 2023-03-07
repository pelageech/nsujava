package ru.nsu.ablaginin;

public abstract class Employee<T extends EmployeeInfo> extends Thread {
  final Pizzeria pizzeria;
  final T info;

  public Employee(Pizzeria pizzeria, T employeeInfo) {
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

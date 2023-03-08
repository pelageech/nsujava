package ru.nsu.ablaginin;

import org.junit.jupiter.api.Test;

class InfoTest {

  @Test
  public void coverage() {
    EmployeeInfo employeeInfo = new EmployeeInfo("Kolya", 22, "male");
    ChefInfo chefInfo = new ChefInfo("Kolya", 23, "male");
    CourierInfo courierInfo = new CourierInfo("Kolyan", 24, "male", 3);

    employeeInfo.age();
    chefInfo.age();
    courierInfo.age();
  }
}
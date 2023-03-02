package ru.nsu.ablaginin;

import java.util.concurrent.TimeUnit;

public class Courier extends Thread {
  private final Pizzeria pizzeria;
  public final CourierInfo courierInfo;
  private int current = 0;
  private final OrderPizzaUnion[] orders;

  public Courier(Pizzeria pizzeria, CourierInfo courierInfo) {
    this.pizzeria = pizzeria;
    this.courierInfo = courierInfo;

    if (courierInfo.capacity() < 1) {
      throw new IllegalArgumentException();
    }
    orders = new OrderPizzaUnion[courierInfo.capacity()];
  }

  public void deliver() throws InterruptedException {
    orders[current++] = pizzeria.getPizzaFromStoreBlocking();

    while (current < courierInfo.capacity()) {
      OrderPizzaUnion newOrder = pizzeria.getPizzaFromStoreNotBlocking();
      if (newOrder != null) {
        orders[current++] = newOrder;
      } else {
        break;
      }
    }

    while (current != 0) {
      System.out.println(
          "Order "
              + orders[current - 1].order().id()
              + ": delivering by "
              + courierInfo.name()
      );
      TimeUnit.SECONDS.sleep(orders[current - 1].order().deliveringTime());
      orders[current - 1].order().putPizza(orders[current - 1].pizza());
      System.out.println(
          "Order "
              + orders[current - 1].order().id()
              + ": delivered! "
              + courierInfo.name()
      );
      current--;
    }
  }

  @Override
  public void run() {
    while (true) {
      try {
        deliver();
      } catch (InterruptedException e) {
        System.out.println(e.getMessage());
      }
    }
  }
}

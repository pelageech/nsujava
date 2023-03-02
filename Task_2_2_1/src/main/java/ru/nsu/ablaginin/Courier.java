package ru.nsu.ablaginin;

import java.util.concurrent.TimeUnit;

/**
 * A courier delivers pizzas to clients.
 */
public class Courier extends Thread {
  private final Pizzeria pizzeria;
  public final CourierInfo courierInfo;
  private int current = 0;
  private final OrderPizzaUnion[] orders;

  /**
   * A constructor of a courier.
   *
   * @param pizzeria where one's works
   * @param courierInfo info about a courier
   */
  public Courier(Pizzeria pizzeria, CourierInfo courierInfo) {
    this.pizzeria = pizzeria;
    this.courierInfo = courierInfo;

    if (courierInfo.capacity() < 1) {
      throw new IllegalArgumentException();
    }
    orders = new OrderPizzaUnion[courierInfo.capacity()];
  }

  /**
   * A courier delivers pizzas to clients. One's takes pizzas as many as possible
   * and starts delivering. Since a courier can take more than one pizza, the first
   * action of taking is blocked, and then next ones aren't. If there aren't pizza in store,
   * and a courier has a pizza, one's leaves for delivering.
   *
   * @throws InterruptedException blocking
   */
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

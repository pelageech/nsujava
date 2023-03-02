package ru.nsu.ablaginin;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Order is a class that contains information about an order for
 * cooking pizza. There id, cooking time, delivering time and a wish.
 * Blocking deque is needed for blocking until a pizza is delivered.
 */
public final class Order {
  private final int id;
  private final int cookingTime;
  private final int deliveringTime;
  private final String order;

  private final BlockingDeque<Pizza> deliveredPizza = new LinkedBlockingDeque<>(1);

  /**
   * A constructor of Order.
   *
   * @param id id
   * @param deliveringTime time for delivering
   * @param cookingTime time for cooking
   * @param order wish
   */
  public Order(int id, int deliveringTime, int cookingTime, String order) {
    this.id = id;
    this.cookingTime = cookingTime;
    this.deliveringTime = deliveringTime;
    this.order = order;
  }

  public int id() {
    return id;
  }

  public int deliveringTime() {
    return deliveringTime;
  }

  public int cookingTime() {
    return cookingTime;
  }

  public String order() {
    return order;
  }

  /**
   * Informs that pizza is delivered by putting a pizza into the order.
   *
   * @param pizza pizza
   */
  public void putPizza(Pizza pizza) {
    deliveredPizza.add(pizza);
  }

  /**
   * Take a pizza from an order. Blocks until a pizza is delivered.
   *
   * @return pizza
   * @throws InterruptedException blocking
   */
  public Pizza getPizza() throws InterruptedException {
    return deliveredPizza.takeFirst();
  }

}

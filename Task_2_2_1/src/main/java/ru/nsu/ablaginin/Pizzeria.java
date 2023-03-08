package ru.nsu.ablaginin;

import com.google.gson.Gson;
import java.io.Reader;
import java.util.Arrays;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * You're welcome to our recently opened pizzeria!
 * You can make an order, and our chefs will cook it for you, and
 * our couriers will deliver it ASAP.
 */
public class Pizzeria extends Thread {
  private final AtomicInteger offerId = new AtomicInteger(0);

  private final Courier[] couriers;
  private final Chef[] chefs;

  private final BlockingDeque<OrderPizzaUnion> storage;

  private final BlockingDeque<Order> orders = new LinkedBlockingDeque<>();

  /**
   * A constructor of pizzeria.
   *
   * @param r Reader - info about a team
   * @param storageCapacity how many pizzas can be stored
   */
  public Pizzeria(Reader r, int storageCapacity) {
    Gson gson = new Gson();

    ChefsAndCouriers chefsAndCouriers = gson.fromJson(r, ChefsAndCouriers.class);

    chefs = new Chef[chefsAndCouriers.chefs().length];
    Arrays.setAll(chefs, i -> new Chef(this, chefsAndCouriers.chefs[i]));

    couriers = new Courier[chefsAndCouriers.couriers().length];
    Arrays.setAll(couriers, i -> new Courier(this, chefsAndCouriers.couriers[i]));

    storage = new LinkedBlockingDeque<>(storageCapacity);
  }

  /**
   * Every customer who calls the function gets FutureTask.
   * He must use .get() to take a pizza from the item.
   *
   * @param deliveringTime time for delivering
   * @param cookingTime time for cooking
   * @param order wishing pizza
   * @return item of waiting for pizza
   */
  public FutureTask<Pizza> orderPizza(int deliveringTime, int cookingTime, String order) {
    Callable<Pizza> task = () -> {
      Order newOrder = addOffer(deliveringTime, cookingTime, order);
      return newOrder.getPizza();
    };
    FutureTask<Pizza> future = new FutureTask<>(task);
    Thread thread = new Thread(future);
    thread.start();

    return future;
  }

  private Order addOffer(int deliveringTime, int cookingTime, String order) {
    Order newOrder = new Order(offerId.getAndAdd(1), deliveringTime, cookingTime, order);
    System.out.println("Offer " + newOrder.id() + ": added for processing");
    orders.add(newOrder);
    return newOrder;
  }

  protected Order takeOffer() throws InterruptedException {
    return orders.takeFirst();
  }

  protected void storePizza(Order order, Pizza pizza) throws InterruptedException {
    System.out.println("Pizza has been moved to the storage, order " + order.id());
    storage.putFirst(new OrderPizzaUnion(order, pizza));
  }

  protected OrderPizzaUnion getPizzaFromStoreBlocking() throws InterruptedException {
    return storage.takeFirst();
  }

  protected OrderPizzaUnion getPizzaFromStoreNotBlocking() {
    return storage.pollFirst();
  }

  @Override
  public void run() {
    for (Chef chef : chefs) {
      chef.start();
    }
    for (Courier courier : couriers) {
      courier.start();
    }
  }

  static final class ChefsAndCouriers {
    private final ChefInfo[] chefs;
    private final CourierInfo[] couriers;

    ChefsAndCouriers(ChefInfo[] chefs, CourierInfo[] couriers) {
      this.chefs = chefs;
      this.couriers = couriers;
    }

    public ChefInfo[] chefs() {
      return chefs;
    }

    public CourierInfo[] couriers() {
      return couriers;
    }
  }
}

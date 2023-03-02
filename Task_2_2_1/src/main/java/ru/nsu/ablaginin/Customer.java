package ru.nsu.ablaginin;

/**
 * The Customer is able to order pizza.
 */
public class Customer {

  /**
   * Customer orders pizza and waits for the pizza until it's delivered.
   *
   * @param pizzeria what pizzeria to order from
   * @param deliveringTime how long it takes to deliver
   * @param cookingTime how long it takes to cook pizza
   * @param wish wishing pizza
   * @return pizza!!!
   */
  public Pizza orderPizza(Pizzeria pizzeria, int deliveringTime, int cookingTime, String wish) {
    // вызывает future.get
    try {
      return pizzeria.orderPizza(deliveringTime, cookingTime, wish).get();
    } catch (Exception e) {
      throw new RuntimeException("Order was unsuccessful:(");
    }
  }

}

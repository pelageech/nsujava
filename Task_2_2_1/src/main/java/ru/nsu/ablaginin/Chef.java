package ru.nsu.ablaginin;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Chef cooks pizza. Information about one's is got from ChefInfo.
 */
public final class Chef extends Employee<ChefInfo> {
  private final Pizzeria pizzeria;
  private final ChefInfo chefInfo;

  /**
   * A chef's constructor.
   *
   * @param pizzeria where the chef works
   * @param chefInfo info about a chef
   */
  public Chef(Pizzeria pizzeria, ChefInfo chefInfo) {
    super(pizzeria, chefInfo);

    if (chefInfo.age() < 0 || chefInfo.age() > 140) {
      throw new IllegalArgumentException();
    }
    if (!Objects.equals(chefInfo.gender(), "male")
        && !Objects.equals(chefInfo.gender(), "female")) {
      throw new IllegalArgumentException();
    }
    this.pizzeria = pizzeria;
    this.chefInfo = chefInfo;
  }

  /**
   * Chef cooks pizza. It blocks since cooking take some time.
   *
   * @throws InterruptedException sleep blocks
   */
  public void work() throws InterruptedException {
    Order order = pizzeria.takeOffer();
    TimeUnit.SECONDS.sleep(order.cookingTime());
    System.out.println("Chef " + chefInfo.name() + " has cooked a pizza for order " + order.id());
    Pizza pizza = new Pizza(order.order());
    pizzeria.storePizza(order, pizza);
  }
}

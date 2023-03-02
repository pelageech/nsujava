package ru.nsu.ablaginin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

class CustomerTest {
  @Test
  public void orderTest() {
    try (InputStream is = getClass().getClassLoader().getResourceAsStream("1.json")
    ) {
      assertNotNull(is);
      try (BufferedReader r = new BufferedReader(new InputStreamReader(is))
      ) {
        Pizzeria pizzeria = new Pizzeria(r, 3); // 4 5 3
        pizzeria.start();
        Customer[] customers = new Customer[5];
        customers[0] = new Customer();
        customers[1] = new Customer();
        customers[2] = new Customer();
        customers[3] = new Customer();
        customers[4] = new Customer();

        Pizza[] pizzas = new Pizza[5];
        Thread[] threads = new Thread[5];

        Runnable task0 = () -> {
          pizzas[0] = customers[0].orderPizza(pizzeria, 12, 6, "четыре сыра");
          System.out.println(pizzas[0].order());
        };
        threads[0] = new Thread(task0);
        Runnable task1 = () -> {
          pizzas[1] = customers[1].orderPizza(pizzeria, 17, 2, "пепперони");
          System.out.println(pizzas[1].order());
        };
        threads[1] = new Thread(task1);
        Runnable task2 = () -> {
          pizzas[2] = customers[2].orderPizza(pizzeria, 5, 7, "гавайская");
          System.out.println(pizzas[2].order());
        };
        threads[2] = new Thread(task2);
        Runnable task3 = () -> {
          pizzas[3] = customers[3].orderPizza(pizzeria, 7, 10, "мясная");
          System.out.println(pizzas[3].order());
        };
        threads[3] = new Thread(task3);
        Runnable task4 = () -> {
          pizzas[4] = customers[4].orderPizza(pizzeria, 10, 5, "из быстронома");
          System.out.println(pizzas[4].order());
        };
        threads[4] = new Thread(task4);

        for (var th : threads) {
          th.start();
        }
        for (var th : threads) {
          th.join();
        }
      }
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void crisis() {
    try (InputStream is = getClass().getClassLoader().getResourceAsStream("1.json")
    ) {
      assertNotNull(is);
      try (BufferedReader r = new BufferedReader(new InputStreamReader(is))
      ) {
        Pizzeria pizzeria = new Pizzeria(r, 3); // 4 5 3
        pizzeria.start();

        Customer[] customers = new Customer[16];
        for (int i = 0; i < customers.length; i++) {
          customers[i] = new Customer();
        }
        Random random = new Random();

        var res = Arrays.stream(customers).parallel().map(
            (x) -> x.orderPizza(
                pizzeria,
                random.nextInt(1, 10),
                random.nextInt(5, 15),
                "пицца пепперони"
            )
        );

        AtomicInteger i = new AtomicInteger();
        res.forEach((x) -> i.getAndIncrement());
        assertEquals(customers.length, i.get());
      }

    } catch (Exception e) {
      fail();
    }
  }
}
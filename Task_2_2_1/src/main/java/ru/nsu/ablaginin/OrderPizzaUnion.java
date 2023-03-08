package ru.nsu.ablaginin;

/**
 * A useful union of putting pizza to a store.
 *
 * @param order info about an order
 * @param pizza pizza
 */
public record OrderPizzaUnion(Order order, Pizza pizza) {
}

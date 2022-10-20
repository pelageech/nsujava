package ru.nsu.ablaginin;

import java.util.Objects;

public class Edge<T extends Comparable<T>> {
  private final Vertex<T> from;
  private final Vertex<T> to;
  private final Integer weight;

  public Edge(Vertex<T> f, Vertex<T> t, Integer w) {
    from = f;
    to = t;
    weight = w;
  }

  public Integer getWeight() {
    return weight;
  }

  public Vertex<T> getFrom() {
    return from;
  }

  public Vertex<T> getTo() {
    return to;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Edge<?> edge = (Edge<?>) o;
    return Objects.equals(from, edge.from) && Objects.equals(to, edge.to) && Objects.equals(weight, edge.weight);
  }

  @Override
  public int hashCode() {
    return Objects.hash(from, to, weight);
  }
}

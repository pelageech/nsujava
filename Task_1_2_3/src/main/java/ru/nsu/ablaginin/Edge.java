package ru.nsu.ablaginin;

import java.util.Objects;

record Edge<T extends Comparable<T>> (Vertex<T> from, Vertex<T> to, Integer weight) {
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

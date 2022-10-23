package ru.nsu.ablaginin;

import java.util.Objects;

/**
 * Vertex is a fundamental atom of the Graph.
 * It contains name (or key) and a couple of params
 * for algorithms.
 *
 * @param <T> type of vertex's name, must be Comparable
 */
public class Vertex<T extends Comparable<T>> {
  private final T key;
  private Integer value;
  private boolean visited;

  public Vertex(T key) {
    this.key = key;
  }

  public T getKey() {
    return key;
  }

  public Integer getValue() {
    return value;
  }

  public boolean isVisited() {
    return visited;
  }

  public void setValue(Integer value) {
    this.value = value;
  }

  public void setVisited(boolean visited) {
    this.visited = visited;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Vertex<?> vertex = (Vertex<?>) o;
    return Objects.equals(key, vertex.key);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key);
  }
}

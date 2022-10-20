package ru.nsu.ablaginin;

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
}

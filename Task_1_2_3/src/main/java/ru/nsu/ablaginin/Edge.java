package ru.nsu.ablaginin;

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

}

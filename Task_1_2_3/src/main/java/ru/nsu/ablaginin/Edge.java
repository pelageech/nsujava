package ru.nsu.ablaginin;

import java.util.Objects;

/**
 * Edge connects vertexes in graph.
 *
 * @param <T> type of vertex's name, must be Comparable
 */
public class Edge<T extends Comparable<T>> {
  private final Vertex<T> from;
  private final Vertex<T> to;
  private final Integer weight;

  /**
   * Implements one edge.
   *
   * @param v1 from-vertex
   * @param v2 to-vertex
   * @param w weight of the edge
   */
  public Edge(Vertex<T> v1, Vertex<T> v2, Integer w) {
    from = v1;
    to = v2;
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
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Edge<?> edge = (Edge<?>) o;
    return Objects.equals(from, edge.from)
        && Objects.equals(to, edge.to);
  }

  @Override
  public int hashCode() {
    return Objects.hash(from, to);
  }
}

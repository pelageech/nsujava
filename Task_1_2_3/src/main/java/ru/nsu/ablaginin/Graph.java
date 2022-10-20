package ru.nsu.ablaginin;

import java.util.*;

public class Graph<T extends Comparable<T>> {

  private final Map<T, Vertex<T>> vertexes;
  private final Map<T, List<Edge<T>>> edges;

  public Graph() {
    vertexes = new HashMap<>();
    edges = new HashMap<>();
  }

  public Graph(T[] vertsArray, int[][] matrix) {
    vertexes = new HashMap<>();
    edges = new HashMap<>();

    int len = vertsArray.length;
    addAllVertexes(vertsArray);

    for (int i = 0; i < len; i++) {
      for (int j = 0; j < len; j++) {
        addEdge(vertsArray[i], vertsArray[j], matrix[i][j]);
      }
    }
  }

  public Graph(T[] vertsArray, List<T>[] listVertexes, List<Integer>[] weights) {
    vertexes = new HashMap<>();
    edges = new HashMap<>();

    int len = vertsArray.length;
    addAllVertexes(vertsArray);

    for (int i = 0; i < len; i++) {
      for (int j = 0; j < listVertexes[i].size(); j++) {
        addEdge(vertsArray[i], listVertexes[i].get(j), weights[i].get(j));
      }
    }
  }

  public boolean addVertex(T key) {
    if (vertexes.containsKey(key)) {
      return false;
    }

    Vertex<T> v = new Vertex<>(key);
    edges.put(v.getKey(), new ArrayList<>());
    vertexes.put(key, v);
    return true;
  }

  public int addAllVertexes(T[] keys) {
    int count = 0;
    for (T k : keys) {
      count = addVertex(k) ? count + 1 : count;
    }
    return count;
  }

  public boolean deleteVertex(T key) {
    edges.remove(key);
    return vertexes.remove(key) != null;
  }

  public boolean addEdge(T key1, T key2, Integer weight) {
    if (weight <= 0) {
      return false;
    }

    Vertex<T> v1 = vertexes.get(key1);
    Vertex<T> v2 = vertexes.get(key2);

    Edge<T> e = new Edge<>(v1, v2, weight);

    edges.get(v1.getKey()).add(e);
    return true;
  }

  public boolean deleteEdge(T key1, T key2) {
    List<Edge<T>> e = edges.get(key1);
    for (int i = 0; i < e.size(); i++) {
      if (e.get(i).getTo().getKey() == key2) {
        e.remove(i);
        return true;
      }
    }
    return false;
  }

  public List<Vertex<T>> dijkstra(T keyStart) {
    if (!vertexes.containsKey(keyStart)) {
      return null;
    }
    Vertex<T> start = vertexes.get(keyStart);

    List<Vertex<T>> result = new ArrayList<>();

    vertexes.forEach((k, v) -> { // add all the vertixes to result and init ones
      result.add(v);
      v.setVisited(false);
      v.setValue(~(0b11 << 30));
    });

    PriorityQueue<Vertex<T>> pq = new PriorityQueue<>(
        Comparator.comparingInt(Vertex::getValue)
    );

    pq.add(start); // init the first Vertex<T>
    start.setValue(0);
    start.setVisited(true);

    while (pq.size() > 0) {
      Vertex<T> cur = pq.remove();
      List<Edge<T>> currEdgeList = edges.get(cur.getKey()); // get connected vertexes

      if (currEdgeList == null) {
        continue;
      }

      for (var i : currEdgeList) {
        Vertex<T> from = i.getFrom();
        Vertex<T> to = i.getTo();

        to.setValue(
            Math.min(to.getValue(), from.getValue() + i.getWeight())
        );

        if (!to.isVisited()) {
          to.setVisited(true);
          pq.add(to);
        }
      }

    }

    result.sort(Comparator.comparingInt(Vertex::getValue));

    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Graph<?> graph = (Graph<?>) o;
    return Objects.equals(vertexes, graph.vertexes) && Objects.equals(edges, graph.edges);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vertexes, edges);
  }
}

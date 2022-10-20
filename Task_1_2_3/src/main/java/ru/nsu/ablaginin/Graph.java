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
    addAll(vertsArray);

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
    addAll(vertsArray);

    for (int i = 0; i < len; i++) {
      for (int j = 0; j < listVertexes[i].size(); j++) {
        addEdge(vertsArray[i], listVertexes[i].get(j), weights[i].get(j));
      }
    }
  }

  public void addVertex(T key) {
    if (vertexes.containsKey(key)) {
      return;
    }

    Vertex<T> v = new Vertex<>(key);
    vertexes.put(key, v);

  }

  public void addAll(T[] keys) {
    for (T k : keys) {
      addVertex(k);
    }
  }

  public void addEdge(Vertex<T> v1, Vertex<T> v2, Integer weight) {
    if (weight == 0) {
      return;
    }

    Edge<T> e = new Edge<>(v1, v2, weight);
    if (!edges.containsKey(v1.getKey())) {
      edges.put(v1.getKey(), new ArrayList<>());
    }

    edges.get(v1.getKey()).add(e);
  }

  public void addEdge(T key1, T key2, Integer weight) {
    Vertex<T> v1 = vertexes.get(key1);
    Vertex<T> v2 = vertexes.get(key2);
    addEdge(v1, v2, weight);
  }

  public List<Vertex<T>> dijkstra(Vertex<T> start) {
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

  public List<Vertex<T>> dijkstra(T keyStart) {
    if (!vertexes.containsKey(keyStart)) {
      return null;
    }

    return dijkstra(vertexes.get(keyStart));
  }
}

package ru.nsu.ablaginin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;

/**
 * Simple graph G = (V, E), where V - list of Vertexes, containing
 * name or just information about vertex, and E - list of
 * Edges kind of (V1, V2, w):
 *  - V1 from-vertex
 *  - V2 to-vertex
 *  - w - weight of the edge (for example a distant between two cities).
 * Simple graph means that for each E1 = (V1, V2, w) and E2 = (V1, V2, w')
 * we have w = w' and E1 = E2.
 *
 * @param <T> type of vertex's name, must be Comparable
 */
public class Graph<T extends Comparable<T>> {

  private final Map<T, Vertex<T>> vertexes;
  private final Map<T, List<Edge<T>>> edges;

  {
    vertexes = new HashMap<>();
    edges = new HashMap<>();
  }

  /**
   * Implements an empty graph.
   */
  public Graph() {

  }

  /**
   * Implements a graph from matrix of adjacency.
   *
   * @param vertexArray array of existing vertexes
   * @param matrix matrix of adjacency
   */
  public Graph(T[] vertexArray, int[][] matrix) {
    int len = vertexArray.length;
    addAllVertexes(vertexArray);

    for (int i = 0; i < len; i++) {
      for (int j = 0; j < len; j++) {
        addEdge(vertexArray[i], vertexArray[j], matrix[i][j]);
      }
    }
  }

  /**
   * Implements a graph from list of adjacency.
   * For each vertex from vertexArray listVertex contains
   * vertexes adjacent to it.
   * "Weights" contains appropriating weights of edges.
   *
   * @param vertexArray array of existing vertexes
   * @param vertexList list of adjacency
   * @param weights list of weights
   */
  public Graph(T[] vertexArray, List<T>[] vertexList, List<Integer>[] weights) {
    int len = vertexArray.length;
    addAllVertexes(vertexArray);

    for (int i = 0; i < len; i++) {
      for (int j = 0; j < vertexList[i].size(); j++) {
        addEdge(vertexArray[i], vertexList[i].get(j), weights[i].get(j));
      }
    }
  }

  /**
   *  Implements a graph from matrix of incidence.
   *
   * @param vertexArray array of existing vertexes
   * @param incidentMatrix matrix with edges
   * @param edgeCount count of edges
   */
  public Graph(T[] vertexArray, int[][] incidentMatrix, int edgeCount) {
    int len = vertexArray.length;
    addAllVertexes(vertexArray);

    for (int i = 0; i < edgeCount; i++) {
      int weight = -1;
      T k1 = null;
      T k2 = null;
      for (int j = 0; j < len; j++) {
        if (incidentMatrix[i][j] > 0) {
          k1 = vertexArray[j];
          weight = incidentMatrix[i][j];
        }
        if (incidentMatrix[i][j] < 0) {
          k2 = vertexArray[j];
        }
      }
      if (k1 != null && k2 != null) {
        addEdge(k1, k2, weight);
      }
    }
  }

  /**
   * Adds new vertex to the graph.
   *
   * @param key key for new vertex
   * @return if the vertex is added, returns true
   */
  public boolean addVertex(T key) {
    if (vertexes.containsKey(key) || key == null) {
      return false;
    }

    Vertex<T> v = new Vertex<>(key);
    edges.put(v.getKey(), new ArrayList<>());
    vertexes.put(key, v);
    return true;
  }

  /**
   * Adds several vertexes to the graph.
   *
   * @param keys array of keys
   * @return number of the added vertexes
   */
  public int addAllVertexes(T[] keys) {
    int count = 0;
    for (T k : keys) {
      count = addVertex(k) ? count + 1 : count;
    }
    return count;
  }

  /**
   * Deletes vertex from the graph.
   *
   * @param key key of the vertex
   * @return if vertex is deleted, returns true
   */
  public boolean deleteVertex(T key) {
    edges.remove(key);
    return vertexes.remove(key) != null;
  }

  /**
   * Adds new edge to the graph.
   *
   * @param key1 from-vertex
   * @param key2 to-vertex
   * @param weight weight of the edge
   * @return if edge is added, returns true
   */
  public boolean addEdge(T key1, T key2, Integer weight) {
    if (weight <= 0 || key1 == null || key2 == null) {
      return false;
    }

    Vertex<T> v1 = vertexes.get(key1);
    Vertex<T> v2 = vertexes.get(key2);

    if (checkEdge(v1, v2)) {
      return false;
    }

    Edge<T> e = new Edge<>(v1, v2, weight);

    edges.get(v1.getKey()).add(e);
    return true;
  }

  /**
   * Deletes edge from the graph.
   *
   * @param key1 from-vertex
   * @param key2 to-vertex
   * @return if edge is deleted, return true
   */
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

  /**
   * Dijkstra's algorithm finds the shortest ways to
   * other vertexes from the starting vertex. For each iteration
   * algorithm takes a vertex with the least value and puts
   * its value + edge's weight to adjacent vertex if its value is
   * higher than new one.
   * If adjacent hasn't been visited yet, it adds to the
   * priority queue. Algorithm ends if queue.size() is zero.
   *
   * @param keyStart algorithm's starting vertex
   * @return sorted list of the vertex by value
   */
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
    for (int i = result.size() - 1; !result.get(i).isVisited(); i--) {
      result.get(i).setValue(null);
    }

    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Graph<?> graph = (Graph<?>) o;
    return Objects.equals(vertexes, graph.vertexes) && Objects.equals(edges, graph.edges);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vertexes, edges);
  }

  private boolean checkEdge(Vertex<T> v1, Vertex<T> v2) {
    List<Edge<T>> e = edges.get(v1.getKey());
    for (Edge<T> edge : e) {
      if (edge.getTo().getKey() == v2.getKey()) {
        return true;
      }
    }
    return false;
  }
}

import java.util.*;
import java.lang.Integer;

public class Graph<T extends Comparable<T>> {

  private final Map<T, Vertex> vertexes;
  private final Map<T, List<Edge>> edges;

  public Graph() {
    vertexes = new Hashtable<>();
    edges = new Hashtable<>();
  }

  public Vertex addVertex(T key) {
    if (vertexes.containsKey(key)) {
      return null;
    }

    Vertex v = new Vertex(key);
    vertexes.put(key, v);

    return v;
  }

  public void addEdge(Vertex v1, Vertex v2, Integer weight) {
    Edge e = new Edge(v1, v2, weight);
    if (!edges.containsKey(v1.key)) {
      edges.put(v1.key, new ArrayList<>());
    }

    edges.get(v1.key).add(e);
  }

  public void addEdge(T key1, T key2, Integer weight) {
    Vertex v1 = vertexes.get(key1);
    Vertex v2 = vertexes.get(key2);
    addEdge(v1, v2, weight);
  }

  public List<Vertex> dijkstra(Vertex start) {
    List<Vertex> result = new ArrayList<>();

    vertexes.forEach((k, v) -> {
      result.add(v);
      v.visited = false;
      v.value = ~(1 << 31) >> 1;
    });
    PriorityQueue<Vertex> pq = new PriorityQueue<>(
        Comparator.comparingInt((Vertex x) -> x.value)
    );

    pq.add(start);
    start.value = 0;
    start.visited = true;

    while(pq.size() > 0) {
      Vertex cur = pq.remove();

      List<Edge> currEdgeList = edges.get(cur.key);
      if (currEdgeList == null) continue;
      for (var i : currEdgeList) {
        i.to.value = Math.min(i.to.value, i.from.value + i.weight);

        if (!i.to.visited) {
          i.to.visited = true;
          pq.add(i.to);
        }
      }
    }

    result.sort(Comparator.comparingInt((Vertex v) -> v.value));

    return result;
  }

  public List<Vertex> dijkstra(T keyStart) {
    if (!vertexes.containsKey(keyStart)) {
      return null;
    }

    return dijkstra(vertexes.get(keyStart));
  }

  public static void main(String[] args) {
    Graph<Integer> G = new Graph<>();
    G.addVertex(3);
    Graph<Integer>.Vertex v = G.addVertex(1);
    G.addVertex(4);
    G.addVertex(2);
    G.addEdge(2, 3, 1);
    G.addEdge(1, 2, 5);
    G.addEdge(1, 4, 9);
    G.addEdge(3, 4, 2);


    List<Graph<Integer>.Vertex> l = G.dijkstra(v);
    for (var i : l) {
      System.out.println(i.key + " " + i.value);
    }
  }

  private class Vertex {
    private final T key;
    private Integer value;
    private boolean visited;

    public Vertex(T key) {
      this.key = key;
    }
  }

  private class Edge {
    private final Vertex from;
    private final Vertex to;
    private final Integer weight;

    public Edge(Vertex f, Vertex t, Integer w) {
      from = f;
      to = t;
      weight = w;
    }
  }

}

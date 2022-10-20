package ru.nsu.ablaginin;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

  @Test
  void addVertex() {

    // actual
    Graph<String> graph = new Graph<>();
    boolean t = graph.addVertex("one");
    boolean f = graph.addVertex("one");

    // expected
    Graph<String> expected = new Graph<>();
    expected.addVertex("one");

    // asserts
    assertEquals(expected, graph);
    assertTrue(t);
    assertFalse(f);
  }

  @Test
  void addAllVertexes() {

    // actual
    Graph<String> graph = new Graph<>();
    int count = graph.addAllVertexes(new String[]{"one", "two", "three", "one"});

    // expected
    Graph<String> expected = new Graph<>();
    expected.addAllVertexes(new String[]{"one", "two", "three"});

    // asserts
    assertEquals(expected, graph);
    assertEquals(3, count);
  }

  @Test
  void deleteVertex() {

    // actual
    Graph<String> graph = new Graph<>();
    graph.addAllVertexes(new String[]{"one", "two", "three", "four"});
    boolean t = graph.deleteVertex("three");
    boolean f = graph.deleteVertex("three");

    // expected
    Graph<String> expected = new Graph<>();
    expected.addAllVertexes(new String[]{"one", "two", "four"});

    // asserts
    assertEquals(expected, graph);
    assertTrue(t);
    assertFalse(f);
  }

  @Test
  void addEdge() {

    // actual
    Graph<String> graph = new Graph<>();
    graph.addAllVertexes(new String[]{"one", "two", "three"});

    graph.addEdge("one", "two", 3);
    graph.addEdge("one", "three", 1);
    boolean t = graph.addEdge("three", "two", 5);
    boolean f = graph.addEdge("two", "three", -3);

    // expected
    Graph<String> expected = new Graph<>();
    expected.addAllVertexes(new String[]{"one", "two", "three"});
    expected.addEdge("one", "two", 3);
    expected.addEdge("one", "three", 1);
    expected.addEdge("three", "two", 5);

    // asserts
    assertEquals(expected, graph);
    assertTrue(t);
    assertFalse(f);
  }

  @Test
  void deleteEdge() {
    // actual
    Graph<String> graph = new Graph<>();
    graph.addAllVertexes(new String[]{"one", "two", "three"});

    graph.addEdge("one", "two", 3);
    graph.addEdge("one", "three", 1);
    graph.addEdge("three", "two", 5);
    graph.addEdge("two", "three", 3);
    boolean t = graph.deleteEdge("two", "three");
    boolean f = graph.deleteEdge("two", "three");

    // expected
    Graph<String> expected = new Graph<>();
    expected.addAllVertexes(new String[]{"one", "two", "three"});
    expected.addEdge("one", "two", 3);
    expected.addEdge("one", "three", 1);
    expected.addEdge("three", "two", 5);

    // asserts
    assertEquals(expected, graph);
    assertTrue(t);
    assertFalse(f);
  }

  @Test
  void dijkstra() {

    // actual
    Graph<String> graph = new Graph<>();
    graph.addAllVertexes(new String[]{"one", "two", "three", "four"});
    graph.addEdge("one", "two", 3);
    graph.addEdge("one", "three", 10);
    graph.addEdge("two", "three", 1);
    graph.addEdge("two", "four", 4);
    graph.addEdge("three", "four", 5);

    List<Vertex<String>> vertexList = graph.dijkstra("one");
    String[] strActual = new String[4];
    Integer[] weightActual = new Integer[4];

    for (int i = 0; i < 4; i++) {
      strActual[i] = vertexList.get(i).getKey();
      weightActual[i] = vertexList.get(i).getValue();
    }

    // expected
    String[] strExpected = new String[]{"one", "two", "three", "four"};
    Integer[] weightsExpected = new Integer[]{0, 3, 4, 7};

    // asserts
    assertArrayEquals(strExpected, strActual);
    assertArrayEquals(weightsExpected, weightActual);
  }

  @Test
  void adjacencyMatrix() throws IOException {
    // actual
    File file = new File("input_1.txt");
    Scanner sc = new Scanner(file);


    int vertexCount = sc.nextInt();

    Integer[] vertexArray = new Integer[vertexCount];
    int[][] weights = new int[vertexCount][vertexCount];

    for (int i = 0; i < vertexCount; i++) {
      vertexArray[i] = sc.nextInt();
    }

    for (int i = 0; i < vertexCount; i++) {
      for (int j = 0; j < vertexCount; j++) {
        weights[i][j] = sc.nextInt();
      }
    }

    Graph<Integer> graph = new Graph<>(vertexArray, weights);

    List<Integer[][]> actual = new ArrayList<>();

    actual.add(new Integer[2][vertexCount]);
    actual.add(new Integer[2][vertexCount]);
    actual.add(new Integer[2][vertexCount]);
    actual.add(new Integer[2][vertexCount]);
    actual.add(new Integer[2][vertexCount]);

    for (int j = 0; j < 5; j++) {
      List<Vertex<Integer>> l1 = graph.dijkstra(sc.nextInt());
      for (int i = 0; i < vertexCount; i++) {
        actual.get(j)[0][i] = l1.get(i).getKey();
        actual.get(j)[1][i] = l1.get(i).getValue();
      }
    }

    // expected
    File file1 = new File("output_1.txt");
    Scanner sc1 = new Scanner(file1);

    List<Integer[][]> expected = new ArrayList<>();

    expected.add(new Integer[2][vertexCount]);
    expected.add(new Integer[2][vertexCount]);
    expected.add(new Integer[2][vertexCount]);
    expected.add(new Integer[2][vertexCount]);
    expected.add(new Integer[2][vertexCount]);

    for (int j = 0; j < 5; j++) {
      for (int i = 0; i < vertexCount; i++) {
        expected.get(j)[0][i] = sc1.nextInt();
        expected.get(j)[1][i] = sc1.nextInt();
      }
    }

    // asserts
    for (int i = 0; i < 5; i++) {
      assertArrayEquals(expected.get(i), actual.get(i));
    }
  }
}
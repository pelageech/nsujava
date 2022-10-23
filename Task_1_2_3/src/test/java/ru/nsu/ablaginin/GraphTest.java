package ru.nsu.ablaginin;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

class GraphTest {

  @Test
  void addVertex() {

    // actual
    Graph<String> graph = new Graph<>();
    boolean t = graph.addVertex("one");
    boolean f = graph.addVertex("one");
    assertTrue(t);
    assertFalse(f);

    // expected
    Graph<String> expected = new Graph<>();
    expected.addVertex("one");

    // asserts
    assertEquals(expected, graph);
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
    assertTrue(t);
    assertFalse(f);

    // expected
    Graph<String> expected = new Graph<>();
    expected.addAllVertexes(new String[]{"one", "two", "four"});

    // asserts
    assertEquals(expected, graph);
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
    boolean f2 = graph.addEdge("one", "two", 5);
    assertTrue(t);
    assertFalse(f);
    assertFalse(f2);

    // expected
    Graph<String> expected = new Graph<>();
    expected.addAllVertexes(new String[]{"one", "two", "three"});
    expected.addEdge("one", "two", 3);
    expected.addEdge("one", "three", 1);
    expected.addEdge("three", "two", 5);

    // asserts
    assertEquals(expected, graph);
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
    assertTrue(t);
    assertFalse(f);

    // expected
    Graph<String> expected = new Graph<>();
    expected.addAllVertexes(new String[]{"one", "two", "three"});
    expected.addEdge("one", "two", 3);
    expected.addEdge("one", "three", 1);
    expected.addEdge("three", "two", 5);

    // asserts
    assertEquals(expected, graph);
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
    File file = new File("src/test/resources/input_1.txt");
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
    int c = sc.nextInt();

    for (int i = 0; i < c; i++) {
      actual.add(new Integer[2][vertexCount]);
    }

    for (int j = 0; j < c; j++) {
      List<Vertex<Integer>> l1 = graph.dijkstra(sc.nextInt());
      for (int i = 0; i < vertexCount; i++) {
        actual.get(j)[0][i] = l1.get(i).getKey();
        actual.get(j)[1][i] = l1.get(i).getValue();
      }
    }

    // expected
    File file1 = new File("src/test/resources/output_1.txt");
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

  @Test
  public void adjacencyList() throws FileNotFoundException {

    // actual
    File file = new File("src/test/resources/input_2.txt");
    Scanner sc = new Scanner(file);

    int vertexCount = sc.nextInt();
    String[] vertexArray = new String[vertexCount];
    List<String>[] vertexList = new List[vertexCount];
    List<Integer>[] weights = new List[vertexCount];

    for (int i = 0; i < vertexCount; i++) {
      vertexList[i] = new ArrayList<>();
      weights[i] = new ArrayList<>();
    }

    for (int i = 0; i < vertexCount; i++) {
      vertexArray[i] = sc.next();
    }

    for (int i = 0; i < vertexCount; i++) {
      int adjs = sc.nextInt();

      for (int j = 0; j < adjs; j++) {
        vertexList[i].add(sc.next());
        weights[i].add(sc.nextInt());
      }
    }

    Graph<String> graph = new Graph<>(vertexArray, vertexList, weights);

    List<String[][]> actual = new ArrayList<>();

    int tests = sc.nextInt();

    for (int i = 0; i < tests; i++) {
      actual.add(new String[2][vertexCount]);
    }

    for (int j = 0; j < tests; j++) {
      List<Vertex<String>> l1 = graph.dijkstra(sc.next());
      for (int i = 0; i < vertexCount; i++) {
        actual.get(j)[0][i] = l1.get(i).getKey();
        actual.get(j)[1][i] = l1.get(i).getValue().toString();
      }
    }

    // expected
    File file1 = new File("src/test/resources/output_2.txt");
    Scanner sc1 = new Scanner(file1);

    int v = 8;

    List<String[][]> expected = new ArrayList<>();

    for (int i = 0; i < 3; i++) {
      expected.add(new String[2][v]);
      for (int j = 0; j < v; j++) {
        expected.get(i)[0][j] = sc1.next();
        expected.get(i)[1][j] = sc1.next();
      }
    }

    // asserts
    for (int i = 0; i < 3; i++) {
      assertArrayEquals(expected.get(i), actual.get(i));
    }
  }

  @Test
  public void incidentMatrix() throws IOException {

    // actual
    File file = new File("src/test/resources/input_3.txt");
    Scanner sc = new Scanner(file);


    int vertexCount = sc.nextInt();
    String[] vertexArray = new String[vertexCount];
    for (int i = 0; i < vertexCount; i++) {
      vertexArray[i] = sc.next();
    }

    int edgeCount = sc.nextInt();
    int[][] edgeMatrix = new int[edgeCount][vertexCount];

    for (int i = 0; i < edgeCount; i++) {
      for (int j = 0; j < vertexCount; j++) {
        edgeMatrix[i][j] = sc.nextInt();
      }
    }

    Graph<String> graph = new Graph<>(vertexArray, edgeMatrix, edgeCount);

    List<String[][]> actual = new ArrayList<>();

    int tests = sc.nextInt();

    for (int i = 0; i < tests; i++) {
      actual.add(new String[2][vertexCount]);
    }

    for (int j = 0; j < tests; j++) {
      List<Vertex<String>> l1 = graph.dijkstra(sc.next());
      for (int i = 0; i < vertexCount; i++) {
        actual.get(j)[0][i] = l1.get(i).getKey();
        if (l1.get(i).getValue() != null) {
          actual.get(j)[1][i] = l1.get(i).getValue().toString();
        } else {
          actual.get(j)[1][i] = "noway";
        }
      }
    }

    // expected
    File file1 = new File("src/test/resources/output_3.txt");
    Scanner sc1 = new Scanner(file1);

    int v = 6;
    int t = 3;

    List<String[][]> expected = new ArrayList<>();

    for (int i = 0; i < t; i++) {
      expected.add(new String[2][v]);
      for (int j = 0; j < v; j++) {
        expected.get(i)[0][j] = sc1.next();
        expected.get(i)[1][j] = sc1.next();
      }
    }

    // asserts
    for (int i = 0; i < t; i++) {
      assertArrayEquals(expected.get(i), actual.get(i));
    }
  }
}
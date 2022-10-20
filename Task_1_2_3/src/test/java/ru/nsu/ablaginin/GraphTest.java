package ru.nsu.ablaginin;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

  @Test
  void addVertex() {
  }

  @Test
  void addAll() {
  }

  @Test
  void addEdge() {
  }

  @Test
  void testAddEdge() {
  }

  @Test
  void dijkstra() {
  }

  @Test
  void testDijkstra() {
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
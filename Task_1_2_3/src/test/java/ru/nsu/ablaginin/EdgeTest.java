package ru.nsu.ablaginin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

class EdgeTest {

  @Test
  void getWeight() {
    Vertex<Integer> v1 = new Vertex<>(27);
    Vertex<Integer> v2 = new Vertex<>(54);

    Edge<Integer> e = new Edge<>(v1, v2, 88);

    assertEquals(88, (int) e.getWeight());
  }

  @Test
  void getFrom() {
    Vertex<Integer> v1 = new Vertex<>(27);
    Vertex<Integer> v2 = new Vertex<>(54);

    Edge<Integer> e = new Edge<>(v1, v2, 88);

    assertEquals(v1, e.getFrom());
  }

  @Test
  void getTo() {
    Vertex<Integer> v1 = new Vertex<>(27);
    Vertex<Integer> v2 = new Vertex<>(54);

    Edge<Integer> e = new Edge<>(v1, v2, 88);

    assertEquals(v2, e.getTo());
  }

  @Test
  void testEquals() {
    Vertex<Integer> v1 = new Vertex<>(27);
    Vertex<Integer> v2 = new Vertex<>(54);
    Vertex<Integer> v3 = new Vertex<>(14);

    Edge<Integer> e1 = new Edge<>(v1, v2, 88);
    Edge<Integer> e2 = new Edge<>(v1, v2, 666);
    Edge<Integer> e3 = new Edge<>(v1, v3, 88);

    // asserts
    assertEquals(e1, e2);
    assertNotEquals(e1, e3);
  }

  @Test
  void testHashCode() {
    Vertex<Integer> v1 = new Vertex<>(27);
    Vertex<Integer> v2 = new Vertex<>(54);
    Vertex<Integer> v3 = new Vertex<>(14);

    Edge<Integer> e1 = new Edge<>(v1, v2, 88);
    Edge<Integer> e2 = new Edge<>(v1, v2, 666);
    Edge<Integer> e3 = new Edge<>(v1, v3, 88);

    // asserts
    assertEquals(e1.hashCode(), e2.hashCode());
    assertNotEquals(e1.hashCode(), e3.hashCode());
  }
}
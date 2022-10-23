package ru.nsu.ablaginin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VertexTest {

  @Test
  void getKey() {
    Vertex<Integer> v = new Vertex<>(4);
    assertEquals(4, (int) v.getKey());
  }

  @Test
  void getValue() {
    Vertex<Integer> v = new Vertex<>(4);
    assertEquals(0, (int) v.getValue());
  }

  @Test
  void isVisited() {
    Vertex<Integer> v = new Vertex<>(4);
    assertFalse(v.isVisited());
  }

  @Test
  void setValue() {
    Vertex<Integer> v = new Vertex<>(4);
    v.setValue(10);
    assertEquals(10, v.getValue());
  }

  @Test
  void setVisited() {
    Vertex<Integer> v = new Vertex<>(4);
    v.setVisited(true);
    assertTrue(v.isVisited());
  }

  @Test
  void testEquals() {
    Vertex<Integer> v1 = new Vertex<>(4);
    v1.setValue(10);
    Vertex<Integer> v2 = new Vertex<>(4);
    v1.setValue(3);
    Vertex<Integer> v3 = new Vertex<>(5);
    v1.setValue(10);

    // asserts
    assertEquals(v1, v2);
    assertNotEquals(v1, v3);
  }

  @Test
  void testHashCode() {
    Vertex<Integer> v1 = new Vertex<>(4);
    v1.setValue(10);
    Vertex<Integer> v2 = new Vertex<>(4);
    v1.setValue(3);
    Vertex<Integer> v3 = new Vertex<>(5);
    v1.setValue(10);

    // asserts
    assertEquals(v1.hashCode(), v2.hashCode());
    assertNotEquals(v1.hashCode(), v3.hashCode());
  }
}
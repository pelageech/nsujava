package ru.nsu.ablaginin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


class VertexTest {

  @Test
  void getKeyTest() {
    Vertex<Integer> v = new Vertex<>(4);
    assertEquals(4, (int) v.getKey());
  }

  @Test
  void getValueTest() {
    Vertex<Integer> v = new Vertex<>(4);
    assertEquals(0, (int) v.getValue());
  }

  @Test
  void isVisitedTest() {
    Vertex<Integer> v = new Vertex<>(4);
    assertFalse(v.isVisited());
  }

  @Test
  void setValueTest() {
    Vertex<Integer> v = new Vertex<>(4);
    v.setValue(10);
    assertEquals(10, v.getValue());
  }

  @Test
  void setVisitedTest() {
    Vertex<Integer> v = new Vertex<>(4);
    v.setVisited(true);
    assertTrue(v.isVisited());
  }

  @Test
  void equalsTest() {
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
  void hashCodeTest() {
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
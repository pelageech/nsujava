package ru.nsu.ablaginin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TreeTest {

  @Test
  void getValue() {

    // actual
    Tree<String> tree = new Tree<>("Hello");
    String actual = tree.getValue();

    // expected
    String expected = "Hello";

    // asserts
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void add() {

    // actual
    Tree<String> tree = new Tree<>("Hello");
    Tree<String> tree1 = tree.add("World!");
    String actual = tree.getValue() + " " + tree1.getValue();

    // expected
    String expected = "Hello World!";

    // asserts
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void getRoot() {
    // actual
    Tree<String> tree = new Tree<>("Hello");
    Tree<String> tree1 = tree.add("World");
    Tree<String> tree2 = tree1.add("!");
    Tree<String> actual = tree2.getRoot();

    // asserts
    Assertions.assertEquals(tree, actual);
  }

  @Test
  void getParent() {

    // actual
    Tree<String> tree = new Tree<>("Hello");
    Tree<String> tree1 = tree.add("World");
    Tree<String> actual = tree1.getParent();

    // asserts
    Assertions.assertEquals(tree, actual);
  }

  @Test
  void getChildren() {

    // actual
    Tree<String> tree = new Tree<>("Hello");
    Tree<String> tree1 = tree.add("World");
    List<Tree<String>> actual = tree.getChildren();

    // expected
    List<Tree<String>> expected = new ArrayList<>();
    expected.add(tree1);

    // asserts
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void getModCounter() {
    // actual
    Tree<String> tree = new Tree<>("Hello");
    Tree<String> tree1 = tree.add("World");
    Tree<String> tree2 = tree1.add("!");
    int actual = tree2.getModCounter();

    // expected
    int expected = 2;
    // asserts
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void delete() {
    // actual
    Tree<Integer> tree = new Tree<>(1);
    Integer nil = tree.delete();

    Tree<Integer> del = tree.add(2);
    Tree<Integer> c1 = del.add(3);
    Tree<Integer> c2 = del.add(4);
    Tree<Integer> c3 = del.add(5);

    Integer actualValue = del.delete();
    List<Tree<Integer>> actualChildren = tree.getChildren();

    // expected
    Integer expectedValue = 2;
    List<Tree<Integer>> expectedChildren = Arrays.asList(c1, c2, c3);

    // asserts
    Assertions.assertNull(nil);
    Assertions.assertEquals(expectedValue, actualValue);
    Assertions.assertEquals(expectedChildren, actualChildren);
  }

  @Test
  void testEquals() {
    // actual
    Tree<Integer> a = new Tree<>(1);
    a.add(2);
    a.add(3);

    Tree<Integer> b = new Tree<>(1);
    b.add(2);
    b.add(3);

    Tree<Integer> contrast = new Tree<>(1);
    contrast.add(2);
    contrast.add(2);

    // asserts
    Assertions.assertEquals(a, b);
    Assertions.assertNotEquals(a, contrast);
  }

  @Test
  void testHashCode() {

    // actual
    Tree<Integer> a = new Tree<>(1);
    a.add(2);
    a.add(3);

    Tree<Integer> b = new Tree<>(1);
    b.add(2);
    b.add(3);

    Tree<Integer> contrast = new Tree<>(1);
    contrast.add(2);
    contrast.add(2);

    // asserts
    Assertions.assertEquals(a.hashCode(), b.hashCode());
    Assertions.assertNotEquals(a.hashCode(), contrast.hashCode());
  }

  @Test
  void iteratorTest() {

    // actual
    Tree<Integer> tree = new Tree<>(1);
    Tree<Integer> c1 = tree.add(2);
    Tree<Integer> c2 = tree.add(3);
    c1.add(4);
    c1.add(5);
    c2.add(6);

    List<Integer> actualListDfs = new ArrayList<>();
    for (Tree<Integer> i : tree) {
      actualListDfs.add(i.getValue());
    }

    tree.setTypeIteration(Tree.IteratorTree.BFS);

    List<Integer> actualListBfs = new ArrayList<>();
    for (Tree<Integer> i : tree) {
      actualListBfs.add(i.getValue());
    }

    // expected
    List<Integer> expectedListDFS = Arrays.asList(1, 3, 6, 2, 5, 4);
    List<Integer> expectedListBFS = Arrays.asList(1, 2, 3, 4, 5, 6);

    // asserts
    Assertions.assertEquals(expectedListDFS, actualListDfs);
    Assertions.assertEquals(expectedListBFS, actualListBfs);
  }

  @Test
  void complexTreeTest() {

    // actual
    Tree<Integer> tree = new Tree<>(1);
    Tree<Integer> v0 = tree.add(2);
    tree.add(3);
    tree.add(4);
    v0.add(5);
    Tree<Integer> v1 = v0.add(6);
    v1.add(7);
    v1.add(8);
    v1.add(9);
    v1.getChildren().get(1).add(10);

    v1.delete();
    v0.add(11);
    v0.delete();
    tree.getChildren().get(5).add(12);
    /* Vertex -> List Children
     * 1 -> [3, 4, 5, 7, 8, 9, 11]
     * 8 -> [10]
     * 9 -> [12]
     */

    List<Integer> actualList = new ArrayList<>();

    for (Tree<Integer> i : tree) {
      actualList.add(i.getValue());
    }

    Integer actualCountChildren = tree.getChildren().size();

    // expected
    List<Integer> expectedList = Arrays.asList(1, 11, 9, 12, 8, 10, 7, 5, 4, 3);
    Integer expectedCountChildren = 7;

    // asserts
    Assertions.assertEquals(expectedList, actualList);
    Assertions.assertEquals(expectedCountChildren, actualCountChildren);
  }
}
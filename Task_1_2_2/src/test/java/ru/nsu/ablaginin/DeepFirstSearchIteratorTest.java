package ru.nsu.ablaginin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DeepFirstSearchIteratorTest {

  @Test
  public void nextTest() {
    // actual
    Tree<Integer> tree = new Tree<>(4);
    tree.add(1);
    tree.add(2);
    tree.add(3);
    DeepFirstSearchIterator<Integer> dfs = new DeepFirstSearchIterator<>(tree);

    List<Integer> actual = new ArrayList<>();
    actual.add(dfs.next().getValue());
    actual.add(dfs.next().getValue());
    actual.add(dfs.next().getValue());
    actual.add(dfs.next().getValue());

    // expected
    List<Integer> expected = Arrays.asList(4, 3, 2, 1);

    // asserts
    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void hasNextTest() {
    // actual
    Tree<Integer> tree = new Tree<>(4);
    tree.add(1);
    tree.add(2);
    tree.add(3);

    DeepFirstSearchIterator<Integer> dfs = new DeepFirstSearchIterator<>(tree);
    List<Boolean> actual = new ArrayList<>();
    actual.add(dfs.hasNext());
    dfs.next();
    actual.add(dfs.hasNext());
    dfs.next();
    actual.add(dfs.hasNext());
    dfs.next();
    actual.add(dfs.hasNext());
    dfs.next();
    actual.add(dfs.hasNext());

    // expected
    List<Boolean> expected = Arrays.asList(true, true, true, true, false);

    // asserts
    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void testConcurrentModificationException() {
    Tree<Integer> tree = new Tree<>(4);
    tree.add(1);
    tree.add(2);
    tree.add(3);

    DeepFirstSearchIterator<Integer> dfs = new DeepFirstSearchIterator<>(tree);
    tree.add(4);
    Assertions.assertThrows(ConcurrentModificationException.class, () -> dfs.next());
  }

  @Test
  public void iteratorTest() {
    // actual
    Tree<Integer> tree = new Tree<>(4);
    tree.add(1);
    tree.add(2);
    tree.add(3);
    List<Tree<Integer>> treeCh = tree.getChildren();
    treeCh.get(0).add(4);
    treeCh.get(0).add(5);
    treeCh.get(0).add(6);
    treeCh.get(0).add(7);
    treeCh.get(1).add(8);
    treeCh.get(2).add(9);
    treeCh.get(2).add(10);
    treeCh.get(2).add(11);
    treeCh.get(1).getChildren().get(0).add(100);
    treeCh.get(1).getChildren().get(0).add(101);

    DeepFirstSearchIterator<Integer> dfs = new DeepFirstSearchIterator<>(tree);
    List<Integer> actual = new ArrayList<>();
    while (dfs.hasNext()) {
      actual.add(dfs.next().getValue());
    }

    // expected
    List<Integer> expected = Arrays.asList(4, 3, 11, 10, 9, 2, 8, 101, 100, 1, 7, 6, 5, 4);

    // asserts
    Assertions.assertEquals(expected, actual);
  }
}
package ru.nsu.ablaginin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BreadthFirstSearchIteratorTest {
  @Test
  public void nextTest() {
    // actual
    Tree<Integer> tree = new Tree<>(4);
    tree.add(1);
    tree.add(2);
    tree.add(3);
    BreadthFirstSearchIterator<Integer> bfs = new BreadthFirstSearchIterator<>(tree);

    List<Integer> actual = new ArrayList<>();
    actual.add(bfs.next().getValue());
    actual.add(bfs.next().getValue());
    actual.add(bfs.next().getValue());
    actual.add(bfs.next().getValue());

    // expected
    List<Integer> expected = Arrays.asList(4, 1, 2, 3);

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

    BreadthFirstSearchIterator<Integer> bfs = new BreadthFirstSearchIterator<>(tree);
    List<Boolean> actual = new ArrayList<>();
    actual.add(bfs.hasNext());
    bfs.next();
    actual.add(bfs.hasNext());
    bfs.next();
    actual.add(bfs.hasNext());
    bfs.next();
    actual.add(bfs.hasNext());
    bfs.next();
    actual.add(bfs.hasNext());

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

    BreadthFirstSearchIterator<Integer> bfs = new BreadthFirstSearchIterator<>(tree);
    tree.add(4);
    Assertions.assertThrows(ConcurrentModificationException.class, () -> bfs.next());
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

    BreadthFirstSearchIterator<Integer> bfs = new BreadthFirstSearchIterator<>(tree);
    List<Integer> actual = new ArrayList<>();
    while (bfs.hasNext()) {
      actual.add(bfs.next().getValue());
    }

    // expected
    List<Integer> expected = Arrays.asList(4, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 100, 101);

    // asserts
    Assertions.assertEquals(expected, actual);
  }
}
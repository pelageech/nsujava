package ru.nsu.ablaginin;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

/**
 * <b>Breadth First Search</b> is an algorithm of tree traversal.
 * It's supposed the root and all the children of the pulled elements
 * is added to the queue. The algorithm guarantees that
 * all the tree's vertices will be concerned by the algorithm.
 */
public class BreadthFirstSearchIterator<T> implements Iterator<Tree<T>> {
  private Tree<T> current;
  private final int modCounter;
  private final List<Tree<T>> queue;

  /**
   * Init BFS Iterator.
   *
   * @param root the root of the tree.
   */
  public BreadthFirstSearchIterator(Tree<T> root) {
    modCounter = root.getModCounter();
    queue = new ArrayList<>();
    queue.add(root);
  }

  /**
   * Returns true if there is the next element.
   *
   * @return bool
   */
  @Override
  public boolean hasNext() {
    return queue.size() > 0;
  }

  /**
   * Returns the next element.
   *
   * @return element.
   */
  @Override
  public Tree<T> next() {
    current = (Tree<T>) queue.remove(0);
    if (modCounter != current.getModCounter()) {
      throw new ConcurrentModificationException();
    }
    queue.addAll(current.getChildren());
    return current;
  }

}

package ru.nsu.ablaginin;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Stack;

/**
 * <b>Deep First Search</b> is an algorithm of tree traversal.
 * It's supposed the root and all the children of the popped elements
 * is pushed to the stack. The algorithm guarantees that
 * all the tree's vertices will be concerned by the algorithm.
 */
public class DeepFirstSearchIterator<T> implements Iterator<Tree<T>> {
  private Tree<T> current;
  private final int modCounter;
  private final Stack<Tree<T>> stack;

  /**
   * Init DFS Iterator.
   *
   * @param root the root of the tree.
   */
  public DeepFirstSearchIterator(Tree<T> root) {
    modCounter = root.getModCounter();
    stack = new Stack<>();
    stack.push(root);
  }

  /**
   * Returns true if there is the next element.
   *
   * @return bool
   */
  @Override
  public boolean hasNext() {
    return stack.size() > 0;
  }

  /**
   * Returns the next element.
   *
   * @return element.
   */
  @Override
  public Tree<T> next() {
    current = stack.pop();
    if (modCounter != current.getModCounter()) {
      throw new ConcurrentModificationException();
    }
    stack.addAll(current.getChildren());
    return current;
  }

}

package ru.nsu.ablaginin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Tree is a hierarchical data structure.
 * If we have some vertex V and vertex U, then
 * we can define the relations for each element of the tree:
 *  - parent(U) = V - V is a U's parent
 *  - children(V) = [U] - get all the V's children
 * Description of the construction of Tree:
 * - root is a Tree;
 * - T is a Tree, (V <- T) and not (U <- T)
 *   if T' is such that parent(U) = V and (T'\U = T), then T' is a Tree
 */
public class Tree<T> implements Iterable<Tree<T>> {
  private final List<Tree<T>> children;
  private int modCounter;
  private Tree<T> parent;
  private final Tree<T> root;
  private final T value;

  /**
   * Tree's iterator type: DFS or BFS.
   */
  public enum IteratorTree { DFS, BFS }

  private static IteratorTree typeIteration;

  /**
   * Create the root of the new tree, thereby
   * initializing the tree.
   *
   * @param val value of the root.
   */
  public Tree(T val) {
    root = this;
    modCounter = 0;
    value = val;
    parent = null;
    children = new ArrayList<>();
    typeIteration = IteratorTree.DFS;
  }

  /**
   * Returns the vertex's children.
   *
   * @return list of children
   */
  public List<Tree<T>> getChildren() {
    return children;
  }

  /**
   * Returns the modification counter.
   *
   * @return modCounter
   */
  public int getModCounter() {
    return root.modCounter;
  }

  /**
   * Returns the vertex's parent.
   *
   * @return parent
   */
  public Tree<T> getParent() {
    return parent;
  }

  /**
   * Returns the root.
   *
   * @return root
   */
  public Tree<T> getRoot() {
    return root;
  }

  /**
   * Return vertex's value.
   *
   * @return value
   */
  public T getValue() {
    return value;
  }

  /**
   * The method creates a new vertex with the value
   * and adds it to the tree.
   * The new vertex is added to the parent's children
   * and returned.
   *
   * @param val new vertex's value
   * @return vertex's reference
   */
  public Tree<T> add(T val) {
    root.modCounter++;
    Tree<T> newBranch = new Tree<>(
        val, this.root, this, new ArrayList<>()
    );

    this.children.add(newBranch);
    return newBranch;
  }

  /**
   * The function removes the vertex from the tree.
   * Vertex's children is added to the parent's children.
   * Call `( 2 ).remove`:
   *                  ( 0 )                               (     0     )
   *                 /  |  \                            /    /    \     \
   *            ( 1 ) (-2-) ( 3 )        |====>     ( 1 ) ( 5 )  ( 6 ) ( 3 )
   *           /     /    \                        /
   *        ( 4 ) ( 5 )  ( 6 )                  ( 4 )
   * So, ( 0 ) has 4 children now.
   * Warning: deleting the root is forbidden!
   *
   * @return value of the deleted branch
   */
  public T delete() {
    root.modCounter++;
    if (parent == null) {
      return null;
    }

    parent.children.remove(this); // remove the current vertex

    for (Tree<T> i : children) { // change children's parents
      i.parent = parent;
    }

    parent.children.addAll(children); // add children to parent

    return value;
  }

  /**
   * Returns true if two trees are equal.
   *
   * @return bool
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Tree<?> tree = (Tree<?>) o;

    return Objects.equals(value, tree.value)
        && Objects.equals(children, tree.children);
  }

  /**
   * Returns tree's hashcode.
   *
   * @return hash
   */
  @Override
  public int hashCode() {
    return Objects.hash(value, children);
  }

  /**
   * The method lets change the iteration's type.
   * There are two types: DFS and BFS.
   *
   * @param newType type of the iteration
   */
  public void setTypeIteration(IteratorTree newType) {
    typeIteration = newType;
  }

  /**
   * Returns the iterator.
   *
   * @return iterator
   */
  @Override
  public Iterator<Tree<T>> iterator() {
    if (typeIteration == IteratorTree.BFS) {
      return new BreadthFirstSearchIterator<T>(this);
    }

    return new DeepFirstSearchIterator<T>(this);
  }

  private Tree(T value, Tree<T> root, Tree<T> parent, List<Tree<T>> children) {
    this.root = root;
    this.value = value;
    this.parent = parent;
    this.children = children;
  }

}


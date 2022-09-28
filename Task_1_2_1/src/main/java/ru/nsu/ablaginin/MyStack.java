package ru.nsu.ablaginin;

import java.util.Arrays;
import java.util.Objects;

/**
 * MyStack is a stack's realization.
 * What is a stack? A stack is a data structure which uses LIFO
 * (LIFO - Last In First Out)
 * It has only 3 fundamental things: push, pop and count.
 * This class has extra 2 methods: push- and popStack.
 */
public class MyStack<T> {
  private int size;
  private int capacity;
  private T[] arrayStack;

  /**
   * Create default stack:
   * Array contains up to 10 objects.
   * Array is empty, so size is zero.
   */
  public MyStack() {
    size = 0;
    capacity = 10;
    arrayStack = (T[]) new Object[capacity];
  }

  /**
   * Returns number of the stack items.
   *
   * @return size
   */
  public int count() {
    return size;
  }

  /**
   * Increases the array if it is full and new item is pushed.
   */
  private void realloc(int newCapacity) {
    arrayStack = Arrays.copyOf(arrayStack, newCapacity);
  }

  /**
   * Inserts new element to the end of the stack.
   * A size of the stack increases by one.
   *
   * @param newItem item for pushing
   */
  public void push(T newItem) {
    if (size == capacity) {
      capacity = capacity * 3 / 2;
      realloc(capacity);
    }

    arrayStack[size++] = newItem;
  }

  /**
   * Pushes all the elements of the stack to another one.
   *
   * @param inputStack stack which pushes to main stack
   */
  public void pushStack(MyStack<T> inputStack) {
    for (int i = 0; i < inputStack.count(); i++) {
      push(inputStack.arrayStack[i]);
    }
  }

  /**
   * Removes the last element from the stack and returns it.
   * If size becomes less than capacity
   *
   * @return poppedElem
   */
  public T pop() {
    if (size == 0) {
      return null;
    }

    T poppedElem = arrayStack[--size];
    arrayStack[size] = null;

    return poppedElem;
  }

  /**
   * Pops 'popLength' elements from the stack to new one and returns it.
   * If 'popLength' is equal or higher than the size of the stack,
   * the method returns a copy of the stack.
   * It returns null when popLength is less than zero.
   *
   * @return resultPoppedStack
   */
  public MyStack<T> popStack(int popLength) {
    if (popLength < 0) {
      return null;
    }

    MyStack<T> resultPoppedStack = new MyStack<T>();

    int newLength = popLength > size ? size : popLength;
    T[] temp = (T[]) new Object[newLength]; // temporary array for popping items

    for (int i = 0; i < newLength; i++) {
      temp[i] = pop(); // fill array
    }

    for (int i = newLength - 1; i >= 0; i--) {
      resultPoppedStack.push(temp[i]); // fill new stack
    }

    return resultPoppedStack;
  }

  /**
   * Returns true if two stacks are equal.
   *
   * @return bool
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MyStack<?> myStack = (MyStack<?>) o;
    return size == myStack.size && capacity == myStack.capacity && Arrays.equals(arrayStack, myStack.arrayStack);
  }


  /**
   * Returns an object's hashcode
   *
   * @return result hashcode
   */
  @Override
  public int hashCode() {
    int result = Objects.hash(size, capacity);
    result = 31 * result + Arrays.hashCode(arrayStack);
    return result;
  }

}

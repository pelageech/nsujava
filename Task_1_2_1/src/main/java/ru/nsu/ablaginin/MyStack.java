package ru.nsu.ablaginin;

import java.util.Arrays;

/**
 * MyStack is a stack's realization.
 * What is a stack? A stack is a data structure which uses LIFO
 * (LIFO - Last In First Out)
 * It has only 3 fundamental things: push, pop and count.
 * This class has extra 2 methods: push- and popStack.
 */
public class MyStack {
  private int size;
  private int capacity;
  private Object[] arrayStack;

  /**
   * Create default stack:
   * Array contains up to 10 objects.
   * Array is empty, so size is zero.
   */
  public MyStack() {
    size = 0;
    capacity = 10;
    arrayStack = new Object[capacity];
  }

  /**
   * @return size
   * Returns number of the stack items.
   */
  public int count() {
    return size;
  }

  /**
   * Increases the array if it is full and new item is pushed.
   */
  private void realloc() {
    capacity = capacity * 3 / 2;
    arrayStack = Arrays.copyOf(arrayStack, capacity);
  }

  /**
   * @param newItem
   * Inserts new element to the end of the stack.
   * A size of the stack increases by one.
   */
  public void push(Object newItem) {
    if (size == capacity)
      realloc();

    arrayStack[size++] = newItem;
  }

  /**
   * @param inputSteak
   * Pushes all the elements of the stack to another one.
   */
  public void pushStack(MyStack inputSteak) {
    for (int i = 0; i < inputSteak.count(); i++) {
      push(inputSteak.arrayStack[i]);
    }
  }

  /**
   * @return poppedElem
   * Removes the last element from the stack and returns it.
   */
  public Object pop() {
    if (size == 0) return null;

    Object poppedElem = arrayStack[--size];
    arrayStack[size] = null;
    return poppedElem;
  }

  /**
   * @return resultPoppedStack
   * Pops 'popLength' elements from the stack to new one and returns it.
   * If 'popLength' is equal or higher than the size of the stack, the method returns a copy of the stack.
   * It returns null when popLength is less than zero.
   */
  public MyStack popStack(int popLength) {
    if (popLength < 0) return null;

    MyStack resultPoppedStack = new MyStack();

    int newLength = popLength > size ? size : popLength;
    Object[] temp = new Object[newLength];

    for (int i = 0; i < newLength; i++)
      temp[i] = pop();

    for (int i = newLength - 1; i >= 0; i--)
      resultPoppedStack.push(temp[i]);

    return resultPoppedStack;
  }

  /**
   * @return bool
   * Returns true if two stacks are equal.
   */
  public boolean equals(MyStack myStack) {
    return Arrays.equals(arrayStack, myStack.arrayStack);
  }
}

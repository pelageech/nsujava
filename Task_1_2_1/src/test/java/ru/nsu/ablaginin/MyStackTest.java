package ru.nsu.ablaginin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MyStackTest {

  @Test
  public void exampleTest() {
    // expected
    MyStack<Integer> expected = new MyStack<Integer>();
    expected.push(2);
    MyStack<Integer> expectedPoppedStack = new MyStack<Integer>();
    expectedPoppedStack.push(7);
    expectedPoppedStack.push(3);

    // actual
    MyStack<Integer> actual = new MyStack<Integer>();
    actual.push(2);
    actual.push(7);

    MyStack<Integer> pushingSteak = new MyStack<Integer>();
    pushingSteak.push(3);
    pushingSteak.push(5);
    actual.pushStack(pushingSteak);
    actual.pop();
    MyStack<Integer> actualPoppedStack = actual.popStack(2);

    // asserts
    Assertions.assertEquals(actual, expected);

    int expectedCount = 1;
    Assertions.assertEquals(
        actual.count(), expectedCount
    );
    Assertions.assertEquals(expectedPoppedStack, actualPoppedStack);
  }

  @Test
  public void popTest() {
    // expected
    MyStack<Integer> expectedFullPoppedStack = new MyStack<Integer>();
    expectedFullPoppedStack.push(54);
    expectedFullPoppedStack.push(27);
    expectedFullPoppedStack.push(228);
    expectedFullPoppedStack.push(314);

    MyStack<Integer> expectedPartialPoppedStack = new MyStack<Integer>();
    expectedPartialPoppedStack.push(2);
    expectedPartialPoppedStack.push(7);
    expectedPartialPoppedStack.push(100);

    // actual
    MyStack<Integer> temp = new MyStack<Integer>();
    temp.push(2);
    temp.push(7);
    temp.push(100);
    temp.push(54);
    temp.push(27);
    temp.push(228);
    temp.push(314);
    temp.push(99);

    MyStack<Integer> nullStack = temp.popStack(-9);
    MyStack<Integer> zeroPoppedStack = temp.popStack(0);
    zeroPoppedStack.pop();
    Integer poppedInteger = temp.pop();
    MyStack<Integer> poppedStackFull = temp.popStack(4);
    MyStack<Integer> partialPoppedStack = temp.popStack(100);

    // asserts
    Assertions.assertEquals(
        99, poppedInteger
    );
    Assertions.assertNull(nullStack);

    MyStack<Integer> empty = new MyStack<Integer>();
    Assertions.assertEquals(empty, zeroPoppedStack);
    Assertions.assertEquals(expectedFullPoppedStack, poppedStackFull);
    Assertions.assertEquals(partialPoppedStack, expectedPartialPoppedStack);
  }

  @Test
  public void increasingCapacityTest() {
    // expected
    MyStack<Integer> expectedStack = new MyStack<Integer>();
    expectedStack.push(1);
    expectedStack.push(2);
    expectedStack.push(3);
    expectedStack.push(4);
    expectedStack.push(5);
    expectedStack.push(6);
    expectedStack.push(7);
    expectedStack.push(8);
    expectedStack.push(9);
    expectedStack.push(10);
    expectedStack.push(11);

    MyStack<Integer> expectedBiggerStack = new MyStack<Integer>();
    expectedBiggerStack = expectedStack;

    expectedBiggerStack.push(12);
    expectedBiggerStack.push(13);
    expectedBiggerStack.push(14);
    expectedBiggerStack.push(15);
    expectedBiggerStack.push(16);
    expectedBiggerStack.push(17);
    expectedBiggerStack.push(18);
    expectedBiggerStack.push(19);

    // actual
    MyStack<Integer> stack = new MyStack<Integer>();

    stack.push(1);
    stack.push(2);
    stack.push(3);
    stack.push(4);
    stack.push(5);
    stack.push(6);
    stack.push(7);
    stack.push(8);
    stack.push(9);
    stack.push(10);
    stack.push(11);

    MyStack<Integer> biggerStack = new MyStack<Integer>();
    biggerStack = stack;

    biggerStack.push(12);
    biggerStack.push(13);
    biggerStack.push(14);
    biggerStack.push(15);
    biggerStack.push(16);
    biggerStack.push(17);
    biggerStack.push(18);
    biggerStack.push(19);

    // asserts
    Assertions.assertEquals(expectedStack, stack);
    Assertions.assertEquals(expectedBiggerStack, biggerStack);
  }

  @Test
  public void emptyStackTest() {
    // expected
    MyStack<Integer> expected = new MyStack<Integer>();
    expected.push(27);
    expected.push(54);
    expected.push(98);

    // actual
    MyStack<Integer> actual = new MyStack<Integer>();
    actual.push(27);
    actual.push(54);
    actual.push(98);
    MyStack<Integer> empty = new MyStack<Integer>();
    empty.pushStack(empty);
    MyStack<Integer> empty2 = new MyStack<>();
    actual.pushStack(empty);

    // asserts
    Assertions.assertEquals(expected, actual);
    Assertions.assertEquals(empty2, empty);
  }
}
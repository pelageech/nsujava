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
  public void poppingTest() {
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

  @Test
  public void countTest() {
    // expected

    // actual
    MyStack<Integer> stack = new MyStack<>();
    stack.push(9);
    stack.push(10);
    stack.push(1);
    stack.push(-1);
    stack.push(0);
    stack.push(2);
    stack.push(8);
    stack.push(19);
    stack.push(1);
    stack.push(92);
    stack.push(54);

    Integer actual = stack.count();

    // asserts
    Integer expected = 11;
    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void pushTest() {
    // expected
    MyStack<Integer> expected = new MyStack<>();
    expected.push(9);
    expected.push(10);
    expected.push(1);
    expected.push(-1);
    expected.push(0);
    expected.push(2);
    expected.push(8);
    expected.push(19);
    expected.push(1);
    expected.push(92);
    expected.push(54);

    // actual
    MyStack<Integer> stack = new MyStack<>();
    stack.push(9);
    stack.push(10);
    stack.push(1);
    stack.push(-1);
    stack.push(0);
    stack.push(2);
    stack.push(8);
    stack.push(19);
    stack.push(1);
    stack.push(92);
    stack.push(54);

    // asserts
    Assertions.assertNotNull(stack);
    Assertions.assertEquals(expected, stack);
  }

  @Test
  public void pushStackTest() {
    // expected
    MyStack<Integer> expected = new MyStack<>();
    expected.push(9);
    expected.push(10);
    expected.push(1);
    expected.push(-1);
    expected.push(0);
    expected.push(2);
    expected.push(8);
    expected.push(19);
    expected.push(1);
    expected.push(92);
    expected.push(54);
    expected.push(8);
    expected.push(19);
    expected.push(1);
    expected.push(92);
    expected.push(54);

    // actual
    MyStack<Integer> stack = new MyStack<>();
    stack.push(9);
    stack.push(10);
    stack.push(1);
    stack.push(-1);
    stack.push(0);
    stack.push(2);
    stack.push(8);
    stack.push(19);
    stack.push(1);
    stack.push(92);
    stack.push(54);

    MyStack<Integer> stack2 = new MyStack<>();
    stack2.push(8);
    stack2.push(19);
    stack2.push(1);
    stack2.push(92);
    stack2.push(54);

    stack.pushStack(stack2);

    // asserts
    Assertions.assertEquals(expected, stack);
  }

  @Test
  public void popTest() {
    // expected
    MyStack<Integer> expected = new MyStack<>();
    expected.push(1);
    expected.push(2);
    expected.push(3);

    // actual
    MyStack<Integer> actual = new MyStack<>();
    actual.push(1);
    actual.push(2);
    actual.push(3);
    actual.push(4);
    actual.push(5);
    actual.pop();
    actual.pop();

    // asserts
    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void popStackTest() {
    //expected
    MyStack<Integer> expected = new MyStack<>();
    expected.push(3);
    expected.push(8);
    expected.push(2);

    // actual
    MyStack<Integer> actual = new MyStack<>();
    actual.push(3);
    actual.push(8);
    actual.push(2);
    actual.push(31);
    actual.push(13);
    actual.push(3);
    actual.push(1113);
    actual.push(31);
    actual.push(311);
    actual.push(1113);
    actual.push(11113);
    actual.push(13);
    actual.push(3);
    actual.push(31);
    actual.push(31);
    actual.push(13);
    actual.popStack(13);

    // asserts
    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void equalsTest() {
    // expected

    // actual
    MyStack<Integer> stack1 = new MyStack<>();
    stack1.push(1);
    stack1.push(2);
    stack1.push(3);
    stack1.push(4);
    stack1.push(5);
    stack1.push(6);
    stack1.push(7);
    stack1.pop();
    stack1.popStack(3);
    stack1.push(4);
    stack1.push(5);
    stack1.push(6);
    stack1.push(7);
    stack1.push(8);

    MyStack<Integer> stack2 = new MyStack<>();
    stack2.push(1);
    stack2.push(2);
    stack2.push(3);
    stack2.push(4);
    stack2.push(5);
    stack2.push(6);
    stack2.push(7);
    stack2.push(8);

    boolean actual = stack1.equals(stack2);

    // asserts
    Assertions.assertTrue(actual);
  }

  public void hashcode() {
    // expected
    MyStack<Integer> stack1 = new MyStack<>();
    stack1.push(1);
    stack1.push(2);
    stack1.push(3);
    stack1.push(4);
    stack1.push(5);
    stack1.push(6);
    stack1.push(7);
    stack1.pop();
    stack1.popStack(3);
    stack1.push(4);
    stack1.push(5);
    stack1.push(6);
    stack1.push(7);
    stack1.push(8);

    // actual
    MyStack<Integer> stack2 = new MyStack<>();
    stack2.push(1);
    stack2.push(2);
    stack2.push(3);
    stack2.push(4);
    stack2.push(5);
    stack2.push(6);
    stack2.push(7);
    stack2.push(8);

    int actual = stack2.hashCode();

    // asserts
    int expected = stack1.hashCode();
    Assertions.assertEquals(expected, actual);
  }
}
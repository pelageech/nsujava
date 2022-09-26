package ru.nsu.ablaginin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MyStackTest {

  @Test
  public void exampleTest() {
    // expected
    MyStack expected = new MyStack();
    expected.push(2);
    MyStack expectedPoppedStack = new MyStack();
    expectedPoppedStack.push(7);
    expectedPoppedStack.push(3);

    // actual
    MyStack actual = new MyStack();
    actual.push(2);
    actual.push(7);

    MyStack pushingSteak = new MyStack();
    pushingSteak.push(3);
    pushingSteak.push(5);
    actual.pushStack(pushingSteak);
    actual.pop();
    MyStack actualPoppedStack = actual.popStack(2);

    // asserts
    Assertions.assertTrue(
        actual.equals(expected)
    );

    int expectedCount = 1;
    Assertions.assertEquals(
        actual.count(), expectedCount
    );
    Assertions.assertTrue(
        expectedPoppedStack.equals(actualPoppedStack)
    );
  }

  @Test
  public void popTest() {
    // expected
    MyStack expectedFullPoppedStack = new MyStack();
    expectedFullPoppedStack.push("Hello");
    expectedFullPoppedStack.push("World!");
    expectedFullPoppedStack.push('q');
    expectedFullPoppedStack.push(3.14);

    MyStack expectedPartialPoppedStack = new MyStack();
    expectedPartialPoppedStack.push(2);
    expectedPartialPoppedStack.push(7);
    expectedPartialPoppedStack.push(100);

    // actual
    MyStack temp = new MyStack();
    temp.push(2);
    temp.push(7);
    temp.push(100);
    temp.push("Hello");
    temp.push("World!");
    temp.push('q');
    temp.push(3.14);
    temp.push(new int[] {1, 3, 3, 7});

    MyStack nullStack = temp.popStack(-9);
    MyStack zeroPoppedStack = temp.popStack(0);
    int[] poppedArray = (int[]) temp.pop();
    MyStack poppedStackFull = temp.popStack(4);
    MyStack partialPoppedStack = temp.popStack(100);

    // asserts
    Assertions.assertArrayEquals(
        poppedArray, new int[] {1, 3, 3, 7}
    );
    Assertions.assertNull(nullStack);

    MyStack empty = new MyStack();
    Assertions.assertTrue(
        empty.equals(zeroPoppedStack)
    );
    Assertions.assertTrue(
        expectedFullPoppedStack.equals(poppedStackFull)
    );
    Assertions.assertTrue(
        partialPoppedStack.equals(expectedPartialPoppedStack)
    );
  }

  @Test
  public void increasingCapacityTest() {
    // expected
    MyStack expectedStack = new MyStack();
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

    MyStack expectedBiggerStack = new MyStack();
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
    MyStack stack = new MyStack();

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

    MyStack biggerStack = new MyStack();
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
    Assertions.assertTrue(
        expectedStack.equals(stack)
    );
    Assertions.assertTrue(
        expectedBiggerStack.equals(biggerStack)
    );
  }

  @Test
  public void emptyStackTest() {
    // expected
    MyStack expected = new MyStack();
    expected.push(27);
    expected.push(54);
    expected.push(98);

    // actual
    MyStack actual = new MyStack();
    actual.push(27);
    actual.push(54);
    actual.push(98);
    MyStack empty = new MyStack();
    empty.pushStack(empty);
    actual.pushStack(empty);

    // asserts
    Assertions.assertTrue(
        expected.equals(actual)
    );
  }
}
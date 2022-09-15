import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

class MyStackTest {

  @Test

  public void exampleTest() {
    MyStack<Integer> expected = new MyStack<>();
    expected.push(2);
    int expC = 1;

    MyStack<Integer> actual = new MyStack<>();
    actual.push(2);
    actual.push(7);
    actual.pushStack(new Integer[] {3, 5});
    actual.pop();
    actual.popStack(2);
    int actC = actual.count();

    Assertions.assertEquals(expC, actC);
    Assertions.assertEquals(
        expected.toList(), actual.toList()
    );
  }

  @Test

  public void longTest() {
    MyStack<String> actual = new MyStack<>();
    actual.push("Hello"); // + Hello
    actual.push("Im"); // + Im
    actual.push("Tom!"); // + Tom!
    actual.pushStack(new String[] {
        "Someone", "told me", "that", "I", "am", "fat."
    });
    actual.pop(); // - fat
    actual.pushStack(
        new String[] {"nice!", "I love it!"}
    );
    actual.pop(); // - I love it!
    MyStack<String> temp = actual.popStack(3); // I am nice! -> temp

    Assertions.assertEquals(6, actual.count());

    actual.pop(); // - that

    Assertions.assertEquals(
        Arrays.asList("Hello", "Im", "Tom!", "Someone", "told me"),
        actual.popStack(5).toList()
    );

    Assertions.assertNull(actual.pop());

    actual.pushStack(temp.toList()); // + I am nice!

    Assertions.assertEquals(Arrays.asList("I", "am", "nice!"), actual.toList());
  }

  @Test

  public void popStackCorrectnessTest() {
    MyStack<Integer> expected = new MyStack<>();
    expected.pushStack(Arrays.asList(1, 5, 6090));

    MyStack<Integer> temp = new MyStack<>();
    temp.push(1);
    temp.pushStack(Arrays.asList(6, 7, 9, 0, 1, 5, 6090, 10));
    temp.pop();

    MyStack<Integer> actual = temp.popStack(3);

    Assertions.assertEquals(
        expected.toList(), actual.toList()
    );
  }
}
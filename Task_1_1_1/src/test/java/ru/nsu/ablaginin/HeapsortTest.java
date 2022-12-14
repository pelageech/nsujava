package ru.nsu.ablaginin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.ablaginin.Heapsort;

/**
 * Test for ru.nsu.ablaginin.Heapsort (method heapsort).
 */
public class HeapsortTest {

  @Test
  public void theFirstTest() {
    List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5);

    List<Integer> actual = Heapsort.heapsort(Arrays.asList(3, 2, 5, 1, 4));

    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void sortedListTest() {
    List<Integer> expected = Arrays.asList(
        1, 3, 6, 10, 10, 19, 80, 900, 1000, 1010
    );

    List<Integer> actual = Heapsort.heapsort(
        Arrays.asList(1, 3, 6, 10, 10, 19, 80, 900, 1000, 1010)
    );

    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void reverseListTest() {
    List<Integer> expected = Arrays.asList(
        1, 3, 6, 10, 10, 19, 80, 900, 1000, 1010
    );

    List<Integer> actual = Heapsort.heapsort(
        Arrays.asList(1010, 1000, 900, 80, 19, 10, 10, 6, 3, 1)
    );

    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void emptyListTest() {
    List<Integer> expected = Collections.emptyList();

    List<Integer> actual = Heapsort.heapsort(
        Collections.emptyList()
    );

    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void oneElementTest() {
    List<Integer> expected = List.of(-6);

    List<Integer> actual = Heapsort.heapsort(
        List.of(-6)
    );

    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void randomListTest() {
    int lengthList = 100;
    List<Integer> expected = new ArrayList<>();

    for (int i = 0; i < lengthList; i++) {
      expected.add(new Random().nextInt());
    }

    List<Integer> actual = Heapsort.heapsort(expected);

    Collections.sort(expected);

    Assertions.assertEquals(expected, actual);
  }
}

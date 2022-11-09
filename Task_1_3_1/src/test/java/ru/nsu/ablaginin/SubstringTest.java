package ru.nsu.ablaginin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

class SubstringTest {
  @Test
  public void commonTest() {

    // actual
    Scanner isc = null;
    InputStream fin = getClass().getClassLoader().getResourceAsStream("f1.in");
    if (fin != null) {
      isc = new Scanner(fin);
    } else {
      fail();
    }

    String textInputString = isc.nextLine();
    InputStream textStream = getClass().getClassLoader().getResourceAsStream(textInputString);
    if (textStream == null) {
      fail();
    }

    Substring fsub = new Substring(textStream);

    List<LinePointers> actual;
    String find = isc.nextLine();

    actual = fsub.algorithmRabinKarp(find);

    // expected
    List<LinePointers> expected = new ArrayList<>();
    expected.add(new LinePointers(1, List.of(0)));
    expected.add(new LinePointers(2, List.of(2)));
    expected.add(new LinePointers(3, List.of(2)));
    expected.add(new LinePointers(4, List.of(7, 24)));
    expected.add(new LinePointers(5, List.of(1)));

    for (var i : actual) {
      System.out.println(i.line() + " " + Arrays.toString(i.pointers().toArray(new Integer[0])));
    }
    // asserts
    assertEquals(expected, actual);
  }

  @Test
  public void warAndPeaceTest() {
    // actual
    Scanner isc = null;
    InputStream fin = getClass().getClassLoader().getResourceAsStream("f2.in");
    if (fin != null) {
      isc = new Scanner(fin);
    } else {
      fail();
    }

    String textInputString = isc.nextLine();
    InputStream textStream = getClass().getClassLoader().getResourceAsStream(textInputString);
    if (textStream == null) {
      fail();
    }

    Substring fsub = new Substring(textStream);

    List<LinePointers> actual;
    String find = isc.nextLine();

    actual = fsub.algorithmRabinKarp(find);

    // expected
    List<LinePointers> expected = new ArrayList<>();
    expected.add(new LinePointers(2967, List.of(37)));
    expected.add(new LinePointers(3258, List.of(158)));
    expected.add(new LinePointers(3323, List.of(25, 38)));
    expected.add(new LinePointers(4058, List.of(687)));
    expected.add(new LinePointers(12310, List.of(67)));
    expected.add(new LinePointers(12655, List.of(1033)));
    expected.add(new LinePointers(12772, List.of(507)));
    expected.add(new LinePointers(13749, List.of(1459)));
    expected.add(new LinePointers(16153, List.of(48, 216)));

    // asserts
    assertEquals(expected, actual);
  }

  @Test
  public void nullInputStreamTest() {

    // actual
    InputStream isc = getClass().getClassLoader().getResourceAsStream("f1.in");

    Substring fileSubstr = new Substring(isc);
    Substring fileSubstring = new Substring(null);

    // asserts
    assertNull(fileSubstring.algorithmRabinKarp("Hello"));
    assertNull(fileSubstr.algorithmRabinKarp(""));
    assertNull(fileSubstr.algorithmRabinKarp(null));
  }

  @Test
  public void overStringTest() {

    // actual
    Scanner isc = null;
    InputStream fin = getClass().getClassLoader().getResourceAsStream("f3.in");
    if (fin != null) {
      isc = new Scanner(fin);
    } else {
      fail();
    }

    String textInputString = isc.nextLine();
    InputStream textStream = getClass().getClassLoader().getResourceAsStream(textInputString);
    if (textStream == null) {
      fail();
    }
    Substring fsub = new Substring(textStream);

    List<LinePointers> actual;
    String find = isc.nextLine();

    actual = fsub.algorithmRabinKarp(find);

    // asserts
    assertNull(actual);
  }

  @Test
  public void largeFileTest() {
    assertTrue(createSparseFile());
    InputStream textStream;
    do {
      textStream = getClass().getClassLoader().getResourceAsStream("test.txt");
    } while (textStream == null);

    Substring fsub = new Substring(textStream);
    List<LinePointers> actual = fsub.algorithmRabinKarp("jjifjiweiweurihbfmnfskdfjiewjf");

    assertTrue(actual.isEmpty());
  }

  private boolean createSparseFile() {
    boolean success = true;
    // String command = "fsutil file createnew ./src/test/resources/test.txt 20000000000";
    String command = "dd if=/dev/zero of=./src/test/resources/test.txt bs=1 count=1 seek=200000";
    Process p;
    try {
      p = Runtime.getRuntime().exec(command);

      p.waitFor();
      p.destroy();
    } catch (IOException | InterruptedException e) {
      fail(e.getLocalizedMessage());
    }
    return success;
  }
}
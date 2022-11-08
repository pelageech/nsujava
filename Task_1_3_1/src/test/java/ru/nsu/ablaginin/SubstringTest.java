package ru.nsu.ablaginin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

class SubstringTest {
  @Test
  public void commonTest() throws IOException {

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
    expected.add(new LinePointers(1, List.of(2)));
    expected.add(new LinePointers(2, List.of(2)));
    expected.add(new LinePointers(3, List.of(7, 24)));

    // asserts
    assertEquals(expected, actual);
  }

  @Test
  public void warAndPeaceTest() throws IOException {
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
  public void nullInputStreamTest() throws IOException {

    // actual
    InputStream isc = getClass().getClassLoader().getResourceAsStream("f1.in");
    InputStream isc1 = null;

    Substring fileSubstr = new Substring(isc);
    Substring fileSubstring = new Substring(isc1);

    // asserts
    assertNull(fileSubstring.algorithmRabinKarp("Hello"));
    assertNull(fileSubstr.algorithmRabinKarp(""));
    assertNull(fileSubstr.algorithmRabinKarp(null));
  }

  @Test
  public void overStringTest() throws IOException {

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
}
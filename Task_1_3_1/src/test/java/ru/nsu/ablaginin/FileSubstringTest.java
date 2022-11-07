package ru.nsu.ablaginin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

class FileSubstringTest {
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

    FileSubstring fsub = new FileSubstring(textStream);

    List<LinePointers> actual;
    String find = isc.nextLine();

    actual = fsub.findSubstring(find);

    // expected
    Scanner osc = null;
    InputStream fout = getClass().getClassLoader().getResourceAsStream("f1.out");
    if (fout != null) {
      osc = new Scanner(fout);
    } else {
      fail();
    }
    List<LinePointers> expected = new ArrayList<>();

    int c = osc.nextInt();
    for (int i = 0; i < c; i++) {
      int line = osc.nextInt();
      int cc = osc.nextInt();
      List<Integer> pointers = new ArrayList<>();

      for (int j = 0; j < cc; j++) {
        pointers.add(osc.nextInt());
      }
      expected.add(new LinePointers(line, pointers));
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

    FileSubstring fsub = new FileSubstring(textStream);

    List<LinePointers> actual;
    String find = isc.nextLine();

    actual = fsub.findSubstring(find);

    // expected
    Scanner osc = null;
    InputStream fout = getClass().getClassLoader().getResourceAsStream("f2.out");
    if (fout != null) {
      osc = new Scanner(fout);
    } else {
      fail();
    }
    List<LinePointers> expected = new ArrayList<>();

    int c = osc.nextInt();
    for (int i = 0; i < c; i++) {
      int line = osc.nextInt();
      int cc = osc.nextInt();
      List<Integer> pointers = new ArrayList<>();

      for (int j = 0; j < cc; j++) {
        pointers.add(osc.nextInt());
      }
      expected.add(new LinePointers(line, pointers));
    }

    // asserts
    assertEquals(expected, actual);
  }

  @Test
  public void nullInputStreamTest() {
    InputStream isc = null;
    FileSubstring fileSubstring = new FileSubstring(isc);

    assertNull(fileSubstring.findSubstring("Hello"));
  }
}
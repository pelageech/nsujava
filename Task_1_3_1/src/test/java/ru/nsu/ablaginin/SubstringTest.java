package ru.nsu.ablaginin;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubstringTest {
  @Test
  public void algorithmRabinKarpTest() {

    // actual
    String str = "Have you ever had a cat? I think they're the cutest category of pets! I do love cats!";
    String find = "cat";

    Substring sub = new Substring(str);

    List<Integer> actual = sub.algorithmRabinKarp(find);

    // expected
    List<Integer> expected = Arrays.asList(20, 52, 80);

    // asserts
    assertEquals(expected, actual);
  }

  @Test
  public void OverSubstringTest() {

    // actual
    String str = "Hello World!";
    String find = "Hello World! Bye-Bye World!";

    Substring sub = new Substring(str);

    List<Integer> actual = sub.algorithmRabinKarp(find);

    // expected

    // asserts
    assertTrue(actual.isEmpty());
  }

  @Test
  public void commonTest() throws IOException {

    // actual
    Scanner isc = null;
    InputStream fin = getClass().getClassLoader().getResourceAsStream("1.in");
    if (fin != null) {
      isc = new Scanner(fin);
    } else {
      fail();
    }

    String input = isc.nextLine();
    Substring ss = new Substring(input);

    int c = isc.nextInt();
    @SuppressWarnings("unchecked")
    List<Integer>[] actual = new ArrayList[c];

    isc.nextLine();
    for (int i = 0; i < c; i++) {
      String sub = isc.nextLine();
      actual[i] = ss.algorithmRabinKarp(sub);
    }

    fin.close();

    // expected
    Scanner osc = null;
    InputStream fout = getClass().getClassLoader().getResourceAsStream("1.out");
    if (fout != null) {
      osc = new Scanner(fout);
    } else {
      fail();
    }

    @SuppressWarnings("unchecked")
    List<Integer>[] expected = new ArrayList[c];

    for (int i = 0; i < c; i++) {
      expected[i] = new ArrayList<>();

      int p = osc.nextInt();
      for (int j = 0; j < p; j++) {
        expected[i].add(osc.nextInt());
      }
    }

    fout.close();

    // asserts
    assertArrayEquals(expected, actual);
  }

  @Test
  public void nullTest() {
    String str = "Hi!";

    Substring sub1 = new Substring(str);
    List<Integer> nullList1 = sub1.algorithmRabinKarp(null);
    assertNull(nullList1);

    Substring sub2 = new Substring(null);
    List<Integer> nullList2 = sub2.algorithmRabinKarp(str);
    assertNull(nullList2);
  }
}
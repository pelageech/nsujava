package ru.nsu.ablaginin;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
  @Test
  public void toStringTest() {
    Book book = new Book();
    assertTrue(book.toString().isEmpty());
  }

  @Test
  public void insertTest() {
    Book book = new Book();
    book.insert("key", "value");

    String s = book.toString();
    String expected = "key: 'value' date=";
    assertTrue(s.contains(expected));
  }

  @Test
  public void removeTest() {
    Book book = new Book();
    book.insert("key", "value");
    book.insert("key1", "value1");
    assertTrue(book.remove("key1"));

    String s = book.toString();
    String expected = "key: 'value' date=";
    assertTrue(s.contains(expected));
  }

  @Test
  public void toStringIntervalTest() {
    Book book = new Book();
    book.insert("key", "value");
    book.insert("key1", "value1");
    book.insert("key2", "value2");
    book.insert("key3", "value3");
    book.insert("key4", "value4");
    book.insert("key15", "value5");

    String s1 = null;
    try {
      s1 = book.toStringInterval(
          "2022-12-30",
          "2022-12-31",
          new String[]{}
      );
    } catch (ParseException e) {
      fail();
    }
    String s2 = null;
    try {
      s2 = book.toStringInterval(
          "2022-12-12 00:00:00",
          "2022-12-31 23:59:59",
          new String[]{"key1"}
      );
    } catch (ParseException e) {
      fail();
    }
    assertTrue(s1.isEmpty());
    assertTrue(s2.contains("key1: 'value1' date="));
    assertTrue(s2.contains("key15: 'value5' date="));
    assertFalse(s2.contains("key2: 'value2' date="));
  }

  @Test
  public void commandLineParseTest() {
    try (
        FileOutputStream f = new FileOutputStream("./book.json", false)
    ){
      var txt = new byte[2];
      txt[0] = '{';
      txt[1] = '}';
      f.write(txt);
    } catch (FileNotFoundException ignored) {

    } catch (IOException e) {
      fail();
    }
    Book book = new Book();
    String[] argv1 = "-add hello world".split(" ");
    book.commandLineParse(argv1);
    String[] argv2 = "-add hello1 world1".split(" ");
    book.commandLineParse(argv2);
    String[] argv3 = "-add hello2 world2".split(" ");
    book.commandLineParse(argv3);
    String[] argv4 = "-add hello3 world3".split(" ");
    book.commandLineParse(argv4);
    String[] argv5 = "-rm hello2".split(" ");
    book.commandLineParse(argv5);
    String[] argv6 = "-show".split(" ");
    book.commandLineParse(argv6);
    String[] argv7 = "-add hello worldworld".split(" ");
    book.commandLineParse(argv7);
    String[] argv8 = "-show 2020-12-12 2023-12-31 ello hell hello1".split(" ");
    book.commandLineParse(argv8);

    try (
        Reader r = new BufferedReader(new InputStreamReader(new FileInputStream("./book.json")))
    ) {
      char[] buf = new char[10000];
      int n = r.read(buf);
      assertEquals(736, n);
      Book book1 = new Gson().fromJson(String.copyValueOf(buf, 0, n), Book.class);
      assertTrue(book1.remove("hello"));
      assertTrue(book1.remove("hello1"));
      assertFalse(book1.remove("hello2"));
      assertTrue(book1.remove("hello3"));
    } catch (Exception e) {
      fail();
    }
  }
}
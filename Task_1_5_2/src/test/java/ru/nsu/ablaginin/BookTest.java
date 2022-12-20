package ru.nsu.ablaginin;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import org.junit.jupiter.api.Test;

class BookTest {
  private static final String FILE_NAME = "./book.json";
  
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
    File file = new File(FILE_NAME);

    try ( // create an empty json file `{}`
        FileOutputStream f = new FileOutputStream(file, false)
    ) {
      var txt = new byte[2];
      txt[0] = '{';
      txt[1] = '}';
      f.write(txt);
    } catch (FileNotFoundException ignored) {
      System.out.println("New file will be created");
    } catch (IOException e) {
      fail();
    }

    Book book = new Book();

    // valid
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

    // invalid
    String[] argv9 = "-show 2020-12-12".split(" ");
    String[] argv10 = "-show ello hell hello1".split(" ");
    book.commandLineParse(argv8);
    assertThrows(RuntimeException.class, () -> book.commandLineParse(argv9));
    assertThrows(RuntimeException.class, () -> book.commandLineParse(argv10));

    try (
        Reader r = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_NAME)))
    ) {
      char[] buf = new char[10000];
      int n = r.read(buf);
      assertTrue(n > 0); // check if we read something
      Book book1 = new Gson().fromJson(String.copyValueOf(buf, 0, n), Book.class);

      assertTrue(book1.remove("hello"));
      assertTrue(book1.remove("hello1"));
      assertFalse(book1.remove("hello2"));
      assertTrue(book1.remove("hello3"));
    } catch (Exception e) {
      fail();
    }
    assertTrue(file.delete()); // created file must be deleted
  }

  @Test
  public void addNotFoundFileTest() {
    Book book = new Book();

    // file doesn't exist but will be created
    String[] argv1 = "-add hello world".split(" ");
    book.commandLineParse(argv1);

    File file = new File(FILE_NAME);
    try (
        Reader r = new BufferedReader(new InputStreamReader(new FileInputStream(file)))
    ) {
      char[] buf = new char[10000];
      int n = r.read(buf);
      Book book1 = new Gson().fromJson(String.copyValueOf(buf, 0, n), Book.class);

      assertNotNull(book1);
      assertTrue(book1.remove("hello"));
    } catch (Exception e) {
      fail();
    }

    assertTrue(file.delete());
  }

  @Test
  public void exceptionOneTest() {
    File file = new File(FILE_NAME);

    Book book = new Book();

    // it's illegal
    String[] argv1 = "-rm hello".split(" ");
    assertThrows(IllegalArgumentException.class, () -> book.commandLineParse(argv1));

    // catch an exception to the bad json file
    try {
      assertTrue(file.createNewFile()); // empty file is bad

      String[] argv2 = "-add hello world".split(" ");
      assertThrows(RuntimeException.class, () -> book.commandLineParse(argv2));
    } catch (IOException e) {
      fail();
    }
    assertTrue(file.delete());
  }
}
package ru.nsu.ablaginin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.jetbrains.annotations.NotNull;

/**
 * Book is a console application that allows you to
 * keep a book with some important records.
 * The book saves to JSON file.
 * -add  key value       : inserts a record to the book
 * -rm   key             : removes a record from the book
 * -show                 : prints a book
 * -show from to argv... : prints records in a period containing all the substrings from argv
 */
public class Book {
  private static final String FILE_NAME = "book.json";
  private final MapBookRecord book = new MapBookRecord();

  /**
   * Inserts new record to the book.
   *
   * @param k key - record's header
   * @param v value is a record
   */
  public void insert(@NotNull String k, @NotNull String v) {
    book.getMap().put(
        k,
        new BookRecord(v)
    );
  }

  /**
   * Removes a record from the book using header.
   *
   * @param k key
   * @return true if record was removed successfully
   */
  public boolean remove(@NotNull String k) {
    return null != book.getMap().remove(k);
  }

  /**
   * Takes all the records for a period containing
   * all the substrings from an array and returns them.
   *
   * @param from start date
   * @param to finish date
   * @param substrings array of strings
   * @return string with records
   */
  public String toStringInterval(
      @NotNull Calendar from,
      @NotNull Calendar to,
      @NotNull String[] substrings
  ) {
    StringBuilder result = new StringBuilder();

    book.getMap().forEach((k, v) -> {
      boolean check = true;
      for (var s : substrings) {
        check &= k.contains(s);
      }
      if (check && v.date().after(from) && v.date().before(to)) {
        result.append(k);
        result.append(": ");
        result.append(v);
      }
    });

    return result.toString();
  }

  /**
   * Takes all the records for a period containing
   * all the substrings from an array and returns them.
   *
   * @param fromString start date string
   * @param toString finish date string
   * @param substrings array of strings
   * @return string with records
   */
  public String toStringInterval(
      @NotNull String fromString,
      @NotNull String toString,
      @NotNull String[] substrings) throws java.text.ParseException {

    var format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    format.applyPattern("yyyy-MM-dd");

    Calendar from = Calendar.getInstance();
    from.setTime(format.parse(fromString));
    Calendar to = Calendar.getInstance();
    to.setTime(format.parse(toString));

    return toStringInterval(from, to, substrings);
  }

  /**
   * Returns a book in string format.
   *
   * @return string
   */
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();

    book.getMap().forEach((k, v) -> {
      result.append(k);
      result.append(": ");
      result.append(v);
    });

    return result.toString();
  }

  /**
   * Takes parameters of a command line and deals
   * with them.
   * It supports such parameters like
   * add
   * rm
   * show in 2 representations (with args and without)
   *
   * @param argv parameters
   */
  public void commandLineParse(@NotNull String[] argv) {
    //public static void main(String[] argv) {
    Options op = new Options();

    // `insert` option
    Option insert = new Option("add", "insert new record to the book, takes key and value");
    insert.setArgs(2);
    insert.setType(String.class);
    op.addOption(insert);

    // `remove` option
    Option remove = new Option("rm", "remove a record from the book");
    remove.setArgs(1);
    remove.setType(String.class);
    op.addOption(remove);

    // `show` option
    Option show = new Option("show", "show table. Can search for records between"
        + " several dates and comparing patterns");
    show.setArgs(22);
    show.setOptionalArg(true);
    op.addOption(show);

    CommandLineParser parser = new DefaultParser();
    HelpFormatter formatter = new HelpFormatter();

    try {
      CommandLine cmd = parser.parse(op, argv);

      // try to open file and deal with it
      Book book = new Book();
      try (Reader r = new InputStreamReader(
          new FileInputStream(FILE_NAME)
      )
      ) {
        book = new Gson().fromJson(r, Book.class);
        if (book == null) {
          throw new ParseException("Couldn't parse the existing file");
        }
      } catch (FileNotFoundException e) { // if file doesn't exist, good if add
        if (!cmd.hasOption("add")) {
          throw new IllegalArgumentException("Operation can't be complete "
              + "because file doesn't exist");
        }
      }

      if (cmd.hasOption("add")) {
        add(book, cmd);
      }
      if (cmd.hasOption("rm")) {
        rm(book, cmd);
      }
      if (cmd.hasOption("show")) {
        show(book, cmd);
      }
    } catch (IOException e) {
      System.out.println("IO error");
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }

  }

  private void add(Book book, CommandLine cmd) throws IOException {
    String[] insertArgs = cmd.getOptionValues("add");
    book.insert(insertArgs[0], insertArgs[1]);

    File f = new File(FILE_NAME);

    f.createNewFile(); // create file if it doesn't exist
    try (Writer w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)))
    ) {
      Gson gson = new GsonBuilder()
          .setPrettyPrinting()
          .create();
      gson.toJson(book, Book.class, w);
    }
  }

  private void rm(Book book, CommandLine cmd) throws IOException {
    String removeArg = cmd.getOptionValue("rm");
    book.remove(removeArg);

    try (Writer w = new BufferedWriter(
        new OutputStreamWriter(new FileOutputStream(FILE_NAME))
    )) {
      Gson gson = new GsonBuilder()
          .setPrettyPrinting()
          .create();
      gson.toJson(book, Book.class, w);
    }
  }

  private void show(Book book, CommandLine cmd) throws ParseException {
    String[] showArgs = cmd.getOptionValues("show");

    if (showArgs == null) {
      System.out.println(book);
    } else if (showArgs.length >= 2) {
      String[] substrings = Arrays.copyOfRange(showArgs, 2, showArgs.length);

      try {
        System.out.println(
            book.toStringInterval(showArgs[0], showArgs[1], substrings)
        );
      } catch (java.text.ParseException e) {
        System.out.println("Incorrect date format");
        throw new RuntimeException(e);
      }
    } else {
      throw new ParseException("Not enough arguments");
    }
  }
}

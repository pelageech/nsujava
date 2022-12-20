package ru.nsu.ablaginin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.jetbrains.annotations.NotNull;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Book is a console application that allows you to
 * keep a book with some important records.
 * The book saves to JSON file.
 *
 * -add  key value       : inserts a record to the book
 * -rm   key             : removes a record from the book
 * -show                 : prints a book
 * -show from to argv... : prints records in a period containing all the substrings from argv
 */
public class Book {
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
  public String toStringInterval(@NotNull Calendar from, @NotNull Calendar to, @NotNull String[] substrings) {
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

  public String toStringInterval(
      @NotNull String fromString,
      @NotNull String toString,
      @NotNull String[] substrings) throws java.text.ParseException {

    String result;
    var format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    format.applyPattern("yyyy-MM-dd");

    Calendar from = Calendar.getInstance();
    from.setTime(format.parse(fromString));
    Calendar to = Calendar.getInstance();
    to.setTime(format.parse(toString));

    result = toStringInterval(from, to, substrings);

    return result;
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
    Option show = new Option("show", "show table. Can search for records between" +
        " several dates and comparing patterns");
    show.setArgs(22);
    show.setOptionalArg(true);
    op.addOption(show);

    CommandLineParser parser = new DefaultParser();
    HelpFormatter formatter = new HelpFormatter();

    try {
      CommandLine cmd = parser.parse(op, argv);

      // try to open file and deal with it
      Book book = new Book();
      try (
          Reader r = new BufferedReader(new InputStreamReader(new FileInputStream("book.json")))
      ) {
        book = new Gson().fromJson(r, Book.class);
        if (book == null) {
          throw new ParseException("Couldn't parse the existing file");
        }
      } catch (IOException ignored) {
        if (!cmd.hasOption("add")) {
          System.out.println("Operation can't be complete because file doesn't exist");
          System.exit(1);
        }
      }

      if (cmd.hasOption("add")) {
        String[] insertArgs = cmd.getOptionValues("add");
        book.insert(insertArgs[0], insertArgs[1]);

        File f = new File("book.json");
        f.createNewFile(); // create file if it doesn't exist

        try (
            Writer w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)))
        ) {
          Gson gson = new GsonBuilder()
              .setPrettyPrinting()
              .create();
          gson.toJson(book, Book.class, w);
        }
      }
      if (cmd.hasOption("rm")) {

        String removeArg = cmd.getOptionValue("rm");
        book.remove(removeArg);

        try (Writer w = new BufferedWriter(
            new OutputStreamWriter(new FileOutputStream("book.json"))
        )) {
          Gson gson = new GsonBuilder()
              .setPrettyPrinting()
              .create();
          gson.toJson(book, Book.class, w);
        }
      }
      if (cmd.hasOption("show")) {

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
          }
        } else {
          throw new ParseException("Not enough arguments");
        }
      }
    } catch (IOException e) {
      System.out.println("IO error");
    } catch (ParseException e) {
      System.out.println(e.getMessage());
      formatter.printHelp("Book", op);

      System.exit(1);
    }

  }
}

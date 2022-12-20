package ru.nsu.ablaginin;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.jetbrains.annotations.NotNull;

/**
 * Literally record for notebook.
 *
 * @param record new note for notebook
 * @param date date of new record
 */
record BookRecord(@NotNull String record, @NotNull Calendar date) {
  /**
   * Creates a new record putting current date.
   *
   * @param newRecord new note for notebook
   */
  public BookRecord(@NotNull String newRecord) {
    this(newRecord, Calendar.getInstance());
  }

  @Override
  public String toString() {
    var format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    var dateString = format.format(date.getTime());

    return "'" + record + "' date=" + dateString + "\n";
  }
}

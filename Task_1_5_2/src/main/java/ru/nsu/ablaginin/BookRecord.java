package ru.nsu.ablaginin;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;

record BookRecord(@NotNull String record, @NotNull Calendar date) {
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

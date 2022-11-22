package ru.nsu.ablaginin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.jupiter.api.Test;

class GradeBookRecordTest {
  @Test
  public void increasingCoverageTest() {
    GradeBookRecord gbr = new GradeBookRecord(
        "OOP",
        5,
        "Shadrina A. A.",
        new GregorianCalendar(2022, Calendar.DECEMBER, 31));

    assertEquals("OOP", gbr.subject());
    assertEquals(5, gbr.grade());
    assertEquals("Shadrina A. A.", gbr.teacher());
    assertEquals(new GregorianCalendar(2022, Calendar.DECEMBER, 31), gbr.date());
  }
}
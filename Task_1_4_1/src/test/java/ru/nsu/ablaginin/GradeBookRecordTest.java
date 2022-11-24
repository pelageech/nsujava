package ru.nsu.ablaginin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class GradeBookRecordTest {
  @Test
  public void increasingCoverageTest() {
    GradeBookRecord gbr = new GradeBookRecord(
        "OOP",
        Grade.EXCELLENT,
        Optional.of("Shadrina A. A."),
        Optional.of(new GregorianCalendar(2022, Calendar.DECEMBER, 31))
    );

    assertEquals("OOP", gbr.subject());
    assertEquals(Grade.EXCELLENT, gbr.grade());
    assertTrue(gbr.teacher().isPresent());
    assertEquals("Shadrina A. A.", gbr.teacher().get());
    assertTrue(gbr.date().isPresent());
    assertEquals(new GregorianCalendar(2022, Calendar.DECEMBER, 31), gbr.date().get());
  }
}
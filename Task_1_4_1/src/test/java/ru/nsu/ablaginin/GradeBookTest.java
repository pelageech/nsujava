package ru.nsu.ablaginin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

class GradeBookTest {
  @Test
  public void putRecordTest() {
    GradeBook book = new GradeBook();

    assertTrue(
        book.putRecord(0, new GradeBookRecord(
          "Math",
          4,
          null,
          null
        ))
    );
    book.putRecord(1, new GradeBookRecord(
        "Music",
        5,
        "Orlova M. S.",
        new GregorianCalendar(2020, Calendar.MAY, 23)
    ));
    assertFalse(
        book.putRecord(2, new GradeBookRecord(
            "WHAT",
            5,
            "Popova P. P.",
            new GregorianCalendar(2020, Calendar.MAY, 23)
        ))
    );

    // null checks
    boolean result = book.putRecord(0, null);
    result |= book.putRecord(0, new GradeBookRecord(
        null,
        3,
        null,
        null
    ));
    result |= book.putRecord(0, new GradeBookRecord(
        "Silly",
        100,
        null,
        null
    ));
    assertFalse(result);
    assertTrue(book.putRecord(1, new GradeBookRecord(
        "Music",
        3,
        "Orlova M. S.",
        new GregorianCalendar(2020, Calendar.MAY, 23)
    )));
  }

  @Test
  public void newSemesterTest() {
    GradeBook book = new GradeBook();

    assertFalse(book.newSemester());

    book.putRecord(1, new GradeBookRecord(
        "Music",
        5,
        "Orlova M. S.",
        new GregorianCalendar(2020, Calendar.MAY, 23)
    ));

    assertTrue(book.newSemester());
    assertEquals(book.getCurrentSemester(), 2);
    assertFalse(book.newSemester());
    for (int i = 0; i < 98; i++) {
      book.putRecord(1, new GradeBookRecord(
          "Music",
          5,
          "Orlova M. S.",
          new GregorianCalendar(2020, Calendar.MAY, 23)
      ));
      book.newSemester();
    }
    assertFalse(book.newSemester());
  }

  @Test
  public void removeRecordTest() {
    GradeBook book = new GradeBook();
    book.putRecord(0, new GradeBookRecord(
        "ДУ и ТФКП",
        5,
        "Хмиль А. В.",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Введение в ИИ",
        4,
        "Серый А. С.",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "ООП",
        5,
        "Шадрина А. А,",
        null
    ));
    book.newSemester();
    book.putRecord(0, new GradeBookRecord(
        "Императивное программирование",
        4,
        "Гатилов С. Ю.",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Операционные системы",
        3,
        "Иртегов Д. В.",
        null
    ));
    assertTrue(
        book.removeRecord(1, "Введение в ИИ")
    );
    assertFalse(
        book.removeRecord(0, "ООП")
    );

    // null checks
    assertFalse(
        book.removeRecord(3, "Операционные системы")
    );
    assertFalse(
        book.removeRecord(1, null)
    );
  }

  @Test
  public void isIncreasedScholarshipTest() {
    GradeBook book = new GradeBook();
    book.putRecord(0, new GradeBookRecord(
        "ДУ и ТФКП",
        5,
        "Хмиль А. В.",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Введение в ИИ",
        4,
        "Серый А. С.",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "ООП",
        5,
        "Шадрина А. А,",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Императивное программирование",
        4,
        "Гатилов С. Ю.",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Операционные системы",
        3,
        "Иртегов Д. В.",
        null
    ));

    assertFalse(
        book.isIncreasedScholarship()
    );

    book.newSemester();
    book.putRecord(0, new GradeBookRecord(
        "ДУ и ТФКП",
        5,
        "Хмиль А. В.",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Введение в ИИ",
        5,
        "Серый А. С.",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "ООП",
        5,
        "Шадрина А. А,",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Императивное программирование",
        5,
        "Гатилов С. Ю.",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Операционные системы",
        5,
        "Иртегов Д. В.",
        null
    ));
    assertTrue(
        book.isIncreasedScholarship()
    );
  }

  @Test
  public void setDiplomaGradeTest() {
    GradeBook book = new GradeBook();

    book.putRecord(0, new GradeBookRecord(
        "ДУ и ТФКП",
        5,
        "Хмиль А. В.",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Введение в ИИ",
        4,
        "Серый А. С.",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "ООП",
        5,
        "Шадрина А. А,",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Императивное программирование",
        4,
        "Гатилов С. Ю.",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Операционные системы",
        4,
        "Иртегов Д. В.",
        null
    ));

    assertFalse(
        book.isIncreasedScholarship()
    );

    book.newSemester();
    book.putRecord(0, new GradeBookRecord(
        "ДУ и ТФКП",
        5,
        "Хмиль А. В.",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Введение в ИИ",
        5,
        "Серый А. С.",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "ООП",
        5,
        "Шадрина А. А,",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Императивное программирование",
        5,
        "Гатилов С. Ю.",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Операционные системы",
        5,
        "Иртегов Д. В.",
        null
    ));
    book.newSemester();
    book.putRecord(0, new GradeBookRecord(
        "ДУ и ТФКП",
        5,
        "Хмиль А. В.",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Введение в ИИ",
        4,
        "Серый А. С.",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "ООП",
        5,
        "Шадрина А. А,",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Императивное программирование",
        5,
        "Гатилов С. Ю.",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Операционные системы",
        5,
        "Иртегов Д. В.",
        null
    ));

    assertFalse(
        book.isIncreasedScholarship()
    );

    book.newSemester();
    book.putRecord(0, new GradeBookRecord(
        "ДУ и ТФКП",
        5,
        "Хмиль А. В.",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Введение в ИИ",
        5,
        "Серый А. С.",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "ООП",
        5,
        "Шадрина А. А,",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Императивное программирование",
        5,
        "Гатилов С. Ю.",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Операционные системы",
        4,
        "Иртегов Д. В.",
        null
    ));
    book.setDiplomaGrade(5);
    assertTrue(
        book.isRedDiploma()
    );

    book.putRecord(0, new GradeBookRecord(
        "Операционные системы-2",
        4,
        "Иртегов Д. В.",
        null
    ));
    assertFalse(
        book.isRedDiploma()
    );

    book.removeRecord(0, "Операционные системы-2");
    book.putRecord(0, new GradeBookRecord(
        "Операционные системы",
        3,
        "Иртегов Д. В.",
        null
    ));
    assertFalse(
        book.isRedDiploma()
    );

    book.removeRecord(0, "Операционные системы");
    assertTrue(
        book.isRedDiploma()
    );

    book.setDiplomaGrade(4);
    assertFalse(
        book.isRedDiploma()
    );
  }
}
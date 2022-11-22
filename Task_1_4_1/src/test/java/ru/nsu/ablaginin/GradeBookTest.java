package ru.nsu.ablaginin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class GradeBookTest {
  @Test
  public void putRecordTest() {
    GradeBook book = new GradeBook("Благинин Артём Сергеевич");

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
    GradeBook book = new GradeBook("Благинин Артём Сергеевич");

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
    GradeBook book = new GradeBook("Благинин Артём Сергеевич");
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
    GradeBook book = new GradeBook("Благинин Артём Сергеевич");
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
    GradeBook book = new GradeBook("Благинин Артём Сергеевич");

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
    assertFalse(book.setDiplomaGrade(6));
    book.setDiplomaGrade(5);
    assertEquals(5, book.getDiplomaGrade());
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

  @Test
  public void myGradeBookTest() {
    GradeBook temp = new GradeBook("Жмышенко Валерий Альбертович");
    GradeBook book = new GradeBook("Благинин Артём Сергеевич");
    assertNotEquals(temp, book);
    assertNotEquals(temp.hashCode(), book.hashCode());

    book.putRecord(0, new GradeBookRecord(
        "Введение в алгебру и анализ",
        5,
        "Васкевич",
        new GregorianCalendar(2021, Calendar.JANUARY, 12)
    ));
    book.putRecord(4, new GradeBookRecord(
        "Введение в дискретную математику и математическую логику",
        5,
        "Власов Д. Ю.",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Декларативное программирование",
        5,
        "Куталев Андрей Витальевич",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Императивное программирование",
        5,
        "Гатилов С. Ю.",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "История России",
        5,
        "Оплаканская",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Основы культуры речи",
        5,
        "Заворина",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Иностранный язык",
        5,
        "Kathrine",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Физкультура и спорт",
        5,
        "Шумейко",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Цифровые платформы",
        5,
        "Иртегов Д. В.",
        null
    ));

    assertEquals("Благинин Артём Сергеевич", book.getStudentName());
    assertTrue(book.isIncreasedScholarship());
    assertEquals(5, book.getStudentId());

    book.newSemester();
    List<Map<String, GradeBookRecord>> lmap = book.getGradeBook();
    assertEquals(2, lmap.size());

    book.putRecord(0, new GradeBookRecord(
        "Введение в алгебру и анализ",
        5,
        "Васкевич",
        new GregorianCalendar(2021, Calendar.JANUARY, 12)
    ));
    book.putRecord(0, new GradeBookRecord(
        "Введение в дискретную математику и математическую логику",
        5,
        "Власов Д. Ю.",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Декларативное программирование",
        5,
        "Куталев Андрей Витальевич",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Императивное программирование",
        5,
        "Гатилов С. Ю.",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Измерительный практикум",
        5,
        "Брагин",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Иностранный язык",
        5,
        "Kathrine",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Физкультура и спорт",
        5,
        "Шумейко",
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Цифровые платформы",
        4,
        "Иртегов Д. В.",
        null
    ));

    assertFalse(book.isIncreasedScholarship());
  }
}
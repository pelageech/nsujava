package ru.nsu.ablaginin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class GradeBookTest {

  @Test
  public void equalsTest() {
    GradeBook book = new GradeBook("Papa");

    assertEquals(book, book);
    assertNotEquals(book, null);
  }

  @Test
  public void putRecordTest() {
    GradeBook book = new GradeBook("Благинин Артём Сергеевич");

    assertTrue(
        book.putRecord(0, new GradeBookRecord(
          "Math",
          Grade.GOOD,
          null,
          null
        ))
    );
    book.putRecord(1, new GradeBookRecord(
        "Music",
        Grade.EXCELLENT,
        Optional.of("Orlova M. S."),
        Optional.of(new GregorianCalendar(2020, Calendar.MAY, 23))
    ));
    assertThrows(IllegalArgumentException.class, () ->
        book.putRecord(2, new GradeBookRecord(
            "WHAT",
            Grade.EXCELLENT,
            Optional.of("Popova P. P."),
            Optional.of(new GregorianCalendar(2020, Calendar.MAY, 23))
        ))
    );

    // null checks
    book.putRecord(0, new GradeBookRecord(
        null,
        Grade.SATISFIED,
        null,
        null
    ));
    assertTrue(book.putRecord(1, new GradeBookRecord(
        "Music",
        Grade.SATISFIED,
        Optional.of("Orlova M. S."),
        Optional.of(new GregorianCalendar(2020, Calendar.MAY, 23))
    )));
  }

  @Test
  public void newSemesterTest() {
    GradeBook book = new GradeBook("Благинин Артём Сергеевич");

    assertFalse(book.newSemester());

    book.putRecord(1, new GradeBookRecord(
        "Music",
        Grade.EXCELLENT,
        Optional.of("Orlova M. S."),
        Optional.of(new GregorianCalendar(2020, Calendar.MAY, 23))
    ));

    assertTrue(book.newSemester());
    assertEquals(book.getCurrentSemester(), 2);
    assertFalse(book.newSemester());
    for (int i = 0; i < 98; i++) {
      book.putRecord(1, new GradeBookRecord(
          "Music",
          Grade.EXCELLENT,
          Optional.of("Orlova M. S."),
          Optional.of(new GregorianCalendar(2020, Calendar.MAY, 23))
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
        Grade.EXCELLENT,
        Optional.of("Хмиль А. В."),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Введение в ИИ",
        Grade.GOOD,
        Optional.of("Серый А. С."),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "ООП",
        Grade.EXCELLENT,
        Optional.of("Шадрина А. А,"),
        null
    ));
    book.newSemester();
    book.putRecord(0, new GradeBookRecord(
        "Императивное программирование",
        Grade.GOOD,
        Optional.of("Гатилов С. Ю."),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Операционные системы",
        Grade.SATISFIED,
        Optional.of("Иртегов Д. В."),
        null
    ));
    assertTrue(
        book.removeRecord(1, "Введение в ИИ")
    );
    assertFalse(
        book.removeRecord(0, "ООП")
    );

    // null checks
    assertThrows(IllegalArgumentException.class, () ->
        book.removeRecord(3, "Операционные системы")
    );
  }

  @Test
  public void isIncreasedScholarshipTest() {
    GradeBook book = new GradeBook("Благинин Артём Сергеевич");
    book.putRecord(0, new GradeBookRecord(
        "ДУ и ТФКП",
        Grade.EXCELLENT,
        Optional.of("Хмиль А. В."),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Введение в ИИ",
        Grade.GOOD,
        Optional.of("Серый А. С."),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "ООП",
        Grade.EXCELLENT,
        Optional.of("Шадрина А. А,"),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Императивное программирование",
        Grade.GOOD,
        Optional.of("Гатилов С. Ю."),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Операционные системы",
        Grade.SATISFIED,
        Optional.of("Иртегов Д. В."),
        null
    ));

    assertFalse(
        book.isIncreasedScholarship()
    );

    book.newSemester();
    book.putRecord(0, new GradeBookRecord(
        "ДУ и ТФКП",
        Grade.EXCELLENT,
        Optional.of("Хмиль А. В."),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Введение в ИИ",
        Grade.EXCELLENT,
        Optional.of("Серый А. С."),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "ООП",
        Grade.EXCELLENT,
        Optional.of("Шадрина А. А,"),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Императивное программирование",
        Grade.EXCELLENT,
        Optional.of("Гатилов С. Ю."),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Операционные системы",
        Grade.EXCELLENT,
        Optional.of("Иртегов Д. В."),
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
        Grade.EXCELLENT,
        Optional.of("Хмиль А. В."),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Введение в ИИ",
        Grade.GOOD,
        Optional.of("Серый А. С."),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "ООП",
        Grade.EXCELLENT,
        Optional.of("Шадрина А. А,"),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Императивное программирование",
        Grade.GOOD,
        Optional.of("Гатилов С. Ю."),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Операционные системы",
        Grade.GOOD,
        Optional.of("Иртегов Д. В."),
        null
    ));

    assertFalse(
        book.isIncreasedScholarship()
    );

    book.newSemester();
    book.putRecord(0, new GradeBookRecord(
        "ДУ и ТФКП",
        Grade.EXCELLENT,
        Optional.of("Хмиль А. В."),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Введение в ИИ",
        Grade.EXCELLENT,
        Optional.of("Серый А. С."),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "ООП",
        Grade.EXCELLENT,
        Optional.of("Шадрина А. А,"),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Императивное программирование",
        Grade.EXCELLENT,
        Optional.of("Гатилов С. Ю."),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Операционные системы",
        Grade.EXCELLENT,
        Optional.of("Иртегов Д. В."),
        null
    ));
    book.newSemester();
    book.putRecord(0, new GradeBookRecord(
        "ДУ и ТФКП",
        Grade.EXCELLENT,
        Optional.of("Хмиль А. В."),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Введение в ИИ",
        Grade.GOOD,
        Optional.of("Серый А. С."),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "ООП",
        Grade.EXCELLENT,
        Optional.of("Шадрина А. А,"),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Императивное программирование",
        Grade.EXCELLENT,
        Optional.of("Гатилов С. Ю."),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Операционные системы",
        Grade.EXCELLENT,
        Optional.of("Иртегов Д. В."),
        null
    ));

    assertFalse(
        book.isIncreasedScholarship()
    );

    book.newSemester();
    book.putRecord(0, new GradeBookRecord(
        "ДУ и ТФКП",
        Grade.EXCELLENT,
        Optional.of("Хмиль А. В."),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Введение в ИИ",
        Grade.EXCELLENT,
        Optional.of("Серый А. С."),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "ООП",
        Grade.EXCELLENT,
        Optional.of("Шадрина А. А,"),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Императивное программирование",
        Grade.EXCELLENT,
        Optional.of("Гатилов С. Ю."),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Операционные системы",
        Grade.GOOD,
        Optional.of("Иртегов Д. В."),
        null
    ));
    book.setDiplomaGrade(Grade.EXCELLENT);
    assertEquals(Grade.EXCELLENT, book.getDiplomaGrade());
    assertTrue(
        book.isRedDiploma()
    );

    book.putRecord(0, new GradeBookRecord(
        "Операционные системы-2",
        Grade.GOOD,
        Optional.of("Иртегов Д. В."),
        null
    ));
    assertFalse(
        book.isRedDiploma()
    );

    book.removeRecord(0, "Операционные системы-2");
    book.putRecord(0, new GradeBookRecord(
        "Операционные системы",
        Grade.SATISFIED,
        Optional.of("Иртегов Д. В."),
        null
    ));
    assertFalse(
        book.isRedDiploma()
    );

    book.removeRecord(0, "Операционные системы");
    assertTrue(
        book.isRedDiploma()
    );

    book.setDiplomaGrade(Grade.GOOD);
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
        Grade.EXCELLENT,
        Optional.of("Васкевич"),
        Optional.of(new GregorianCalendar(2021, Calendar.JANUARY, 12))
    ));
    assertThrows(IllegalArgumentException.class, () -> book.putRecord(4, new GradeBookRecord(
        "Введение в дискретную математику и математическую логику",
        Grade.EXCELLENT,
        Optional.of("Власов Д. Ю."),
        null
    )));
    book.putRecord(0, new GradeBookRecord(
        "Декларативное программирование",
        Grade.EXCELLENT,
        Optional.of("Куталев Андрей Витальевич"),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Императивное программирование",
        Grade.EXCELLENT,
        Optional.of("Гатилов С. Ю."),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "История России",
        Grade.EXCELLENT,
        Optional.of("Оплаканская"),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Основы культуры речи",
        Grade.EXCELLENT,
        Optional.of("Заворина"),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Иностранный язык",
        Grade.EXCELLENT,
        Optional.of("Kathrine"),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Физкультура и спорт",
        Grade.EXCELLENT,
        Optional.of("Шумейко"),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Цифровые платформы",
        Grade.EXCELLENT,
        Optional.of("Иртегов Д. В."),
        null
    ));

    assertEquals("Благинин Артём Сергеевич", book.getStudentName());
    assertTrue(book.isIncreasedScholarship());
    assertEquals(7, book.getStudentId());

    book.newSemester();
    List<SubjectToGrade> lmap = book.getGradeBook();
    assertEquals(2, lmap.size());

    book.putRecord(0, new GradeBookRecord(
        "Введение в алгебру и анализ",
        Grade.EXCELLENT,
        Optional.of("Васкевич"),
        Optional.of(new GregorianCalendar(2021, Calendar.JANUARY, 12))
    ));
    book.putRecord(0, new GradeBookRecord(
        "Введение в дискретную математику и математическую логику",
        Grade.EXCELLENT,
        Optional.of("Власов Д. Ю."),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Декларативное программирование",
        Grade.EXCELLENT,
        Optional.of("Куталев Андрей Витальевич"),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Императивное программирование",
        Grade.EXCELLENT,
        Optional.of("Гатилов С. Ю."),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Измерительный практикум",
        Grade.EXCELLENT,
        Optional.of("Брагин"),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Иностранный язык",
        Grade.EXCELLENT,
        Optional.of("Kathrine"),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Физкультура и спорт",
        Grade.EXCELLENT,
        Optional.of("Шумейко"),
        null
    ));
    book.putRecord(0, new GradeBookRecord(
        "Цифровые платформы",
        Grade.GOOD,
        Optional.of("Иртегов Д. В."),
        null
    ));

    assertFalse(book.isIncreasedScholarship());
  }

  @Test
  public void toStringTest() {
    var book = new GradeBook("Artyom");
    book.putRecord(0, new GradeBookRecord(
        "Maths",
        Grade.EXCELLENT,
        Optional.of("Galina"),
        Optional.of(new GregorianCalendar(2022, Calendar.DECEMBER, 12))
    ));
    book.putRecord(0, new GradeBookRecord(
        "Maths-2",
        Grade.EXCELLENT,
        Optional.of("Galina"),
        Optional.of(new GregorianCalendar(2022, Calendar.DECEMBER, 12))
    ));
    book.putRecord(0, new GradeBookRecord(
        "Maths-3",
        Grade.GOOD,
        Optional.of("Galina"),
        Optional.of(new GregorianCalendar(2022, Calendar.DECEMBER, 12))
    ));

    book.newSemester();
    book.putRecord(0, new GradeBookRecord(
        "Maths-3",
        Grade.GOOD,
        Optional.of("Galina"),
        Optional.of(new GregorianCalendar(2022, Calendar.DECEMBER, 12))
    ));

    System.out.println(book);
  }
}
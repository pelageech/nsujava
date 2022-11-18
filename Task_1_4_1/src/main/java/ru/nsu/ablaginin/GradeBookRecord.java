package ru.nsu.ablaginin;

import java.util.Calendar;

public record GradeBookRecord(String subject, Integer grade, String teacher, Calendar date) {

}

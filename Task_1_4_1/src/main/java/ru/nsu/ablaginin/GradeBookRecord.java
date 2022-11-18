package ru.nsu.ablaginin;

import java.util.Calendar;

/**
 * Record for the grade book.
 *
 * @param subject name of the subject
 * @param grade grade the student has got
 * @param teacher the teacher who gives a course
 * @param date the date of grade
 */
public record GradeBookRecord(String subject, Integer grade, String teacher, Calendar date) {

}

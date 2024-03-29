package ru.nsu.ablaginin.dsl.bricks;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 * Marks are given for students.
 */
@Data
@AllArgsConstructor
public class Mark {
    private MarkNum mark;
    private LocalDate date;
}
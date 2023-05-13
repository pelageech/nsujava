package ru.nsu.ablaginin.dsl.bricks;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Mark {
    private MarkNum mark;
    private LocalDate date;
}
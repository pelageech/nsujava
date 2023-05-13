package ru.nsu.ablaginin.dsl.bricks;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Class {
    private LocalDate date;
    private boolean attendance;

}

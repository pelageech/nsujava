package ru.nsu.ablaginin.dsl.bricks;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Class {
    private LocalDate date;
    private boolean attendance;

}

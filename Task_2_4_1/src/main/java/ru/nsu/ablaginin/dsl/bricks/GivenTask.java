package ru.nsu.ablaginin.dsl.bricks;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 * Task is had students to do.
 */
@Data
@AllArgsConstructor
public class GivenTask {
    private String id;
    private LocalDate date;


}

package ru.nsu.ablaginin.bricks;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GivenTask {
    private String id;
    private LocalDate date;

}

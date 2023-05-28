package ru.nsu.ablaginin.dsl.bricks;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Task {
    private String id;
    private String name;
    private int score;

    /**
     * (ref.) See Group() constructor.
     */
    public Task() {
        
    }
}

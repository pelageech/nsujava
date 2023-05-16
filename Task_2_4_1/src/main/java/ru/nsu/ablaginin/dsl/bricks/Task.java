package ru.nsu.ablaginin.dsl.bricks;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Task {
    private String id;
    private String name;
    private int score;

    public Task() {
        
    }

    public void methodMissing(String name, Object args) {
        System.out.println(name + " was called with " + args.toString());
    }
}

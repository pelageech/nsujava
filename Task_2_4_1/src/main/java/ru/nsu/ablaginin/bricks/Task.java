package ru.nsu.ablaginin.bricks;

import lombok.Data;

@Data
public class Task {
    private String id;
    private String name;
    private int score;

    public void methodMissing(String name, Object args) {
        System.out.println(name + " was called with " + args.toString());
    }
}

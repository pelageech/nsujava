package ru.nsu.ablaginin.bricks;

import groovy.lang.Closure;

import java.util.ArrayList;
import java.util.List;

public class MarkList {
    private final List<Mark> marks = new ArrayList<>();

    public void mark(Closure c) {
        Mark mark = new Mark();
        c.setDelegate(mark);
        c.setResolveStrategy(Closure.DELEGATE_ONLY);
        marks.add(mark);
        c.call();
    }

    public void methodMissing(String name, Object args) {
        System.out.println(name + " was called with " + args.toString());
    }

    @Override
    public String toString() {
        return "Marks{"
                + "marks=" + marks
                + '}';
    }
}

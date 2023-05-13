package ru.nsu.ablaginin.dsl.bricks.lists;

import groovy.lang.Closure;
import lombok.Data;
import ru.nsu.ablaginin.dsl.bricks.Mark;
import ru.nsu.ablaginin.dsl.bricks.MarkNum;
import ru.nsu.ablaginin.helper.HelperDSL;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MarkList {
    private final List<Mark> marks = new ArrayList<>();

    public void mark(Closure c) {
        var markString = new MarkString();
        c.setDelegate(markString);
        c.setResolveStrategy(Closure.DELEGATE_ONLY);

        c.call();

        Mark mark = new Mark(
                MarkNum.fromInt(markString.mark),
                LocalDate.parse(markString.date, HelperDSL.FORMATTER)
        );
        marks.add(mark);
    }

    public void methodMissing(String name, Object args) {
        System.out.println(name + " was called with " + args.toString());
    }

    @Data
    private static class MarkString {
        private int mark;
        private String date;
    }

    @Override
    public String toString() {
        return "Marks{"
                + "marks=" + marks
                + '}';
    }
}

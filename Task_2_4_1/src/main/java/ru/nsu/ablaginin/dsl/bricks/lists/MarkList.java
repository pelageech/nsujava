package ru.nsu.ablaginin.dsl.bricks.lists;

import groovy.lang.Closure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import ru.nsu.ablaginin.dsl.bricks.Mark;
import ru.nsu.ablaginin.dsl.bricks.MarkNum;
import ru.nsu.ablaginin.helper.HelperDsl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * List of classes that are parsed by Groovy.
 */
@AllArgsConstructor
@Data
public class MarkList {
    @Getter private final List<Mark> marks = new ArrayList<>();

    /**
     * Parses a mark and adds it to the lost.
     *
     * @param c closure
     */
    public void mark(Closure<?> c) {
        var markString = new MarkString();
        c.setDelegate(markString);
        c.setResolveStrategy(Closure.DELEGATE_ONLY);

        c.call();

        Mark mark = new Mark(
                MarkNum.fromInt(markString.mark),
                LocalDate.parse(markString.date, HelperDsl.FORMATTER)
        );
        marks.add(mark);
    }

    /**
     * The class that are parsed first. After that the function above
     * parses the data to a convienient form and then adds to the list.
     */
    @Data
    private static class MarkString {
        private int mark;
        private String date;
    }
}

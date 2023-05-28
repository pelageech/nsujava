package ru.nsu.ablaginin.dsl.bricks.lists;

import groovy.lang.Closure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import ru.nsu.ablaginin.dsl.bricks.GivenTask;
import ru.nsu.ablaginin.helper.HelperDsl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * List of classes that are parsed by Groovy.
 */
@AllArgsConstructor
@Data
public class GivenTaskList {
    @Getter private final List<GivenTask> givenTaskList = new ArrayList<>();

    /**
     * Parses a task and adds it to the list.
     *
     * @param c closure
     */
    public void task(Closure<?> c) {
        var givenTaskString = new GivenTaskString();

        c.setDelegate(givenTaskString);
        c.setResolveStrategy(Closure.DELEGATE_ONLY);
        c.call();

        var givenTask = new GivenTask(
                givenTaskString.id,
                LocalDate.parse(givenTaskString.date, HelperDsl.FORMATTER)
        );
        givenTaskList.add(givenTask);
    }

    /**
     * The class that are parsed first. After that the function above
     * parses the data to a convienient form and then adds to the list.
     */
    @Data
    private static class GivenTaskString {
        private String id;
        private String date;
    }
}

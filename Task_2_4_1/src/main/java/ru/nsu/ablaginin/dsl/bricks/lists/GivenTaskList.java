package ru.nsu.ablaginin.dsl.bricks.lists;

import groovy.lang.Closure;
import lombok.Data;
import ru.nsu.ablaginin.dsl.bricks.GivenTask;
import ru.nsu.ablaginin.helper.HelperDSL;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GivenTaskList {
    private final List<GivenTask> givenTaskList = new ArrayList<>();

    public void task(Closure c) {
        var givenTaskString = new GivenTaskString();

        c.setDelegate(givenTaskString);
        c.setResolveStrategy(Closure.DELEGATE_ONLY);
        c.call();

        var givenTask = new GivenTask(
                givenTaskString.id,
                LocalDate.parse(givenTaskString.date, HelperDSL.FORMATTER)
        );
        givenTaskList.add(givenTask);
    }

    public void methodMissing(String name, Object args) {
        System.out.println(name + " was called with " + args.toString());
    }

    @Data
    private static class GivenTaskString {
        private String id;
        private String date;
    }

    @Override
    public String toString() {
        return "GivenTaskList{"
                + "givenTaskList=" + givenTaskList
                + '}';
    }
}

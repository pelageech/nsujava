package ru.nsu.ablaginin.dsl.bricks.lists;

import groovy.lang.Closure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import ru.nsu.ablaginin.dsl.bricks.GivenTask;
import ru.nsu.ablaginin.helper.HelperDSL;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class GivenTaskList {
    @Getter private final List<GivenTask> givenTaskList = new ArrayList<>();

    public void task(Closure<?> c) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GivenTaskList that = (GivenTaskList) o;
        return Objects.equals(givenTaskList, that.givenTaskList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(givenTaskList);
    }
}

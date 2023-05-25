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

@AllArgsConstructor
@Data
public class GivenTaskList {
    @Getter private final List<GivenTask> givenTaskList = new ArrayList<>();

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

    public void methodMissing(String name, Object args) {
        System.out.println(name + " was called with " + args.toString());
    }

    @Data
    private static class GivenTaskString {
        private String id;
        private String date;
    }
}

package ru.nsu.ablaginin.dsl.bricks.lists;

import groovy.lang.Closure;
import ru.nsu.ablaginin.dsl.bricks.GivenTask;
import ru.nsu.ablaginin.dsl.bricks.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final List<Task> taskList = new ArrayList<>();

    public void task(Closure c) {
        var task = new Task();
        c.setDelegate(task);
        c.setResolveStrategy(Closure.DELEGATE_ONLY);
        taskList.add(task);
        c.call();
    }

    public void methodMissing(String name, Object args) {
        System.out.println(name + " was called with " + args.toString());
    }

    @Override
    public String toString() {
        return "GivenTaskList{"
                + "givenTaskList=" + taskList
                + '}';
    }
}

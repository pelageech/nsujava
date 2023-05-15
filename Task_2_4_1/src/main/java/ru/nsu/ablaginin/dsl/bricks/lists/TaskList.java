package ru.nsu.ablaginin.dsl.bricks.lists;

import groovy.lang.Closure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.nsu.ablaginin.dsl.bricks.GivenTask;
import ru.nsu.ablaginin.dsl.bricks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class TaskList {
    @Getter private final List<Task> taskList = new ArrayList<>();

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskList taskList1 = (TaskList) o;
        return Objects.equals(taskList, taskList1.taskList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskList);
    }
}

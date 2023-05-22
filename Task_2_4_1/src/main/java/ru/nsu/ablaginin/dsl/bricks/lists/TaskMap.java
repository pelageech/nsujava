package ru.nsu.ablaginin.dsl.bricks.lists;

import groovy.lang.Closure;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.nsu.ablaginin.dsl.bricks.Task;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
public class TaskMap {
    @Getter private final Map<String, Task> taskMap = new HashMap<>();

    public void task(Closure<?> c) {
        var task = new Task();
        c.setDelegate(task);
        c.setResolveStrategy(Closure.DELEGATE_ONLY);
        c.call();
        taskMap.put(task.getId(), task);
    }

    public void methodMissing(String name, Object args) {
        System.out.println(name + " was called with " + args.toString());
    }

    @Override
    public String toString() {
        return "GivenTaskList{"
                + "givenTaskList=" + taskMap
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskMap taskList1 = (TaskMap) o;
        return Objects.equals(taskMap, taskList1.taskMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskMap);
    }
}

package ru.nsu.ablaginin.dsl.bricks.lists;

import groovy.lang.Closure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import ru.nsu.ablaginin.dsl.bricks.Task;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@Data
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
}

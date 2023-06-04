package ru.nsu.ablaginin.dsl.bricks.lists;

import groovy.lang.Closure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import ru.nsu.ablaginin.dsl.bricks.Task;

import java.util.HashMap;
import java.util.Map;

/**
 * Map of classes that are parsed by Groovy.
 */
@AllArgsConstructor
@Data
public class TaskMap {
    @Getter private final Map<String, Task> taskMap = new HashMap<>();

    /**
     * Parses a task and adds it to the map.
     *
     * @param c closure
     */
    public void task(Closure<?> c) {
        var task = new Task();
        c.setDelegate(task);
        c.setResolveStrategy(Closure.DELEGATE_ONLY);
        c.call();
        taskMap.put(task.getId(), task);
    }
}

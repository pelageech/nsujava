package ru.nsu.ablaginin.dsl;

import groovy.lang.Closure;
import lombok.Data;
import ru.nsu.ablaginin.dsl.bricks.Student;
import ru.nsu.ablaginin.dsl.bricks.lists.TaskMap;

@Data
public class DSL {
    private Student student;
    private TaskMap taskMap;

    public void student(Closure<?> c) {
        student = new Student();
        c.setDelegate(student);
        c.setResolveStrategy(Closure.DELEGATE_ONLY);
        c.call();
    }

    public void tasks(Closure<?> c) {
        taskMap = new TaskMap();
        c.setDelegate(taskMap);
        c.setResolveStrategy(Closure.DELEGATE_ONLY);
        c.call();
    }
}

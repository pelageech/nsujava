package ru.nsu.ablaginin.dsl;

import groovy.lang.Closure;
import lombok.Data;
import ru.nsu.ablaginin.dsl.bricks.Student;
import ru.nsu.ablaginin.dsl.bricks.lists.TaskMap;

/**
 * The main configure that can be parsed by Groovy DSL.
 */
@Data
public class Dsl {
    private Student student;
    private TaskMap taskMap;

    /**
     * Parses a student.
     *
     * @param c closure
     */
    public void student(Closure<?> c) {
        student = new Student();
        c.setDelegate(student);
        c.setResolveStrategy(Closure.DELEGATE_ONLY);
        c.call();
    }

    /**
     * Parses tasks.
     *
     * @param c closure
     */
    public void tasks(Closure<?> c) {
        taskMap = new TaskMap();
        c.setDelegate(taskMap);
        c.setResolveStrategy(Closure.DELEGATE_ONLY);
        c.call();
    }
}

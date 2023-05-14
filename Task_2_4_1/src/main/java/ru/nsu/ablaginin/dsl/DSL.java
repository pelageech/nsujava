package ru.nsu.ablaginin.dsl;

import groovy.lang.Closure;
import lombok.Data;
import ru.nsu.ablaginin.dsl.bricks.Group;
import ru.nsu.ablaginin.dsl.bricks.Student;
import ru.nsu.ablaginin.dsl.bricks.lists.StudentList;
import ru.nsu.ablaginin.dsl.bricks.lists.TaskList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class DSL {
    private Student student;
    private TaskList taskList;

    public void student(Closure c) {
        c.setDelegate(student);
        c.setResolveStrategy(Closure.DELEGATE_ONLY);
        c.call();
    }

    public void tasks(Closure c) {
        taskList = new TaskList();
        c.setDelegate(taskList);
        c.setResolveStrategy(Closure.DELEGATE_ONLY);
        c.call();
    }
}

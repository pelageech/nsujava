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
    private StudentList students = new StudentList();
    private Map<Group, Student> studentsByGroup = new HashMap<>();
    private TaskList taskList;

    public void students(Closure c) {
        c.setDelegate(students);
        c.setResolveStrategy(Closure.DELEGATE_ONLY);
        c.call();
        for (var s : students.getStudentList()) {
            studentsByGroup.put(s.getGroup(), s);
        }
    }

    public void tasks(Closure c) {
        taskList = new TaskList();
        c.setDelegate(taskList);
        c.setResolveStrategy(Closure.DELEGATE_ONLY);
        c.call();
    }
}

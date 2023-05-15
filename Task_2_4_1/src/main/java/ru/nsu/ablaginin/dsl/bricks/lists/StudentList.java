package ru.nsu.ablaginin.dsl.bricks.lists;

import groovy.lang.Closure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.nsu.ablaginin.dsl.bricks.Student;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class StudentList {
    @Getter
    private final List<Student> studentList = new ArrayList<>();

    public void student(Closure c) {
        var student = new Student();
        c.setDelegate(student);
        c.setResolveStrategy(Closure.DELEGATE_ONLY);
        c.call();
        studentList.add(student);
    }

    public void methodMissing(String name, Object args) {
        System.out.println(name + " was called with " + args.toString());
    }

    @Override
    public String toString() {
        return "GivenTaskList{"
                + "givenTaskList=" + studentList
                + '}';
    }
}

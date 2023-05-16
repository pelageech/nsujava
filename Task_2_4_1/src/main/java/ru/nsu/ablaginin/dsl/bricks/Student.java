package ru.nsu.ablaginin.dsl.bricks;

import groovy.lang.Closure;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.nsu.ablaginin.dsl.bricks.lists.ClassList;
import ru.nsu.ablaginin.dsl.bricks.lists.GivenTaskList;
import ru.nsu.ablaginin.dsl.bricks.lists.MarkList;

@AllArgsConstructor
@Data
public class Student {
    private String nickname;
    private String name;
    private Group group;
    private String url;
    private MarkList markList = new MarkList();
    private GivenTaskList givenTaskList = new GivenTaskList();
    private ClassList classes = new ClassList();

    public Student() {

    }

    public void group(Closure<?> c) {
        group = new Group();
        c.setDelegate(group);
        c.setResolveStrategy(Closure.DELEGATE_ONLY);
        c.call();
    }

    public void marks(Closure<?> c) {
        c.setDelegate(markList);
        c.setResolveStrategy(Closure.DELEGATE_ONLY);
        c.call();
    }

    public void tasks(Closure<?> c) {
        c.setDelegate(givenTaskList);
        c.setResolveStrategy(Closure.DELEGATE_ONLY);
        c.call();
    }

    public void classes(Closure<?> c) {
        c.setDelegate(classes);
        c.setResolveStrategy(Closure.DELEGATE_ONLY);
        c.call();
    }

    public void methodMissing(String name, Object args) {
        System.out.println(name + " was called with " + args.toString());
    }
}

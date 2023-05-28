package ru.nsu.ablaginin.dsl.bricks;

import groovy.lang.Closure;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.nsu.ablaginin.dsl.bricks.lists.ClassList;
import ru.nsu.ablaginin.dsl.bricks.lists.GivenTaskList;
import ru.nsu.ablaginin.dsl.bricks.lists.MarkList;

/**
 * An object of an ordinary and peaceful student.
 */
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

    /**
     * (ref.) See Group() constructor.
     */
    public Student() {

    }

    /**
     * DSL parses a group.
     *
     * @param c closure
     */
    @SuppressWarnings("unused")
    public void group(Closure<?> c) {
        group = new Group();
        c.setDelegate(group);
        c.setResolveStrategy(Closure.DELEGATE_ONLY);
        c.call();
    }

    /**
     * DSL parses marks.
     *
     * @param c closure
     */
    @SuppressWarnings("unused")
    public void marks(Closure<?> c) {
        c.setDelegate(markList);
        c.setResolveStrategy(Closure.DELEGATE_ONLY);
        c.call();
    }

    /**
     * DSL parses tasks.
     *
     * @param c closure
     */
    @SuppressWarnings("unused")
    public void tasks(Closure<?> c) {
        c.setDelegate(givenTaskList);
        c.setResolveStrategy(Closure.DELEGATE_ONLY);
        c.call();
    }

    /**
     * DSL parses classes.
     *
     * @param c closure
     */
    public void classes(Closure<?> c) {
        c.setDelegate(classes);
        c.setResolveStrategy(Closure.DELEGATE_ONLY);
        c.call();
    }

    /**
     * An error handler.
     *
     * @param name function's name
     * @param args arguments
     */
    @SuppressWarnings("unused")
    public void methodMissing(String name, Object args) {
        System.out.println(name + " was called with " + args.toString());
    }
}

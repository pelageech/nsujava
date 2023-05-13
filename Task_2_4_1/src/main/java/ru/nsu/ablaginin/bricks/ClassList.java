package ru.nsu.ablaginin.bricks;

import groovy.lang.Closure;

import java.util.ArrayList;
import java.util.List;

public class ClassList {
    private final List<Class> classList = new ArrayList<>();

    public void newClass(Closure c) {
        Class newClass = new Class();
        c.setDelegate(newClass);
        c.setResolveStrategy(Closure.DELEGATE_ONLY);
        c.call();
        classList.add(newClass);
    }

    @Override
    public String toString() {
        return "ClassList{"
                + "classList=" + classList
                + '}';
    }
}

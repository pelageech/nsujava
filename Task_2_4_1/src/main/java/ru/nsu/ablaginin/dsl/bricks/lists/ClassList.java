package ru.nsu.ablaginin.dsl.bricks.lists;

import groovy.lang.Closure;
import lombok.Data;
import ru.nsu.ablaginin.dsl.bricks.Class;
import ru.nsu.ablaginin.helper.HelperDSL;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClassList {
    private final List<Class> classList = new ArrayList<>();

    public void newClass(Closure c) {
        var classString = new ClassString();
        c.setDelegate(classString);
        c.setResolveStrategy(Closure.DELEGATE_ONLY);
        c.call();

        Class newClass = new Class(
                LocalDate.parse(classString.date, HelperDSL.FORMATTER),
                classString.attendance
        );
        classList.add(newClass);
    }

    @Data
    private static class ClassString {
        private String date;
        private boolean attendance;
    }

    @Override
    public String toString() {
        return "ClassList{"
                + "classList=" + classList
                + '}';
    }
}

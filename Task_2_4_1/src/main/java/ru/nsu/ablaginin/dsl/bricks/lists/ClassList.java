package ru.nsu.ablaginin.dsl.bricks.lists;

import groovy.lang.Closure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import ru.nsu.ablaginin.dsl.bricks.Class;
import ru.nsu.ablaginin.helper.HelperDsl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class ClassList {
    @Getter
    private final List<Class> classList = new ArrayList<>();

    public void newClass(Closure<?> c) {
        var classString = new ClassString();
        c.setDelegate(classString);
        c.setResolveStrategy(Closure.DELEGATE_ONLY);
        c.call();

        Class newClass = new Class(
                LocalDate.parse(classString.date, HelperDsl.FORMATTER),
                classString.attendance
        );
        classList.add(newClass);
    }

    @Data
    private static class ClassString {
        private String date;
        private boolean attendance;
    }
}

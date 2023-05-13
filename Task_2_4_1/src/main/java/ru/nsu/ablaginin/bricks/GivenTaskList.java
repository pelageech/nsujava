package ru.nsu.ablaginin.bricks;

import groovy.lang.Closure;

import java.util.ArrayList;
import java.util.List;

public class GivenTaskList {
    private final List<GivenTask> givenTaskList = new ArrayList<>();

    public void task(Closure c) {
        GivenTask givenTask = new GivenTask();
        c.setDelegate(givenTask);
        c.setResolveStrategy(Closure.DELEGATE_ONLY);
        givenTaskList.add(givenTask);
        c.call();
    }

    public void methodMissing(String name, Object args) {
        System.out.println(name + " was called with " + args.toString());
    }

    @Override
    public String toString() {
        return "GivenTaskList{" +
                "givenTaskList=" + givenTaskList +
                '}';
    }
}

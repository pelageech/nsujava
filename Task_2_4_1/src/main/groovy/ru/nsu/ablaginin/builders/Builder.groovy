package ru.nsu.ablaginin.builders

import lombok.SneakyThrows
import ru.nsu.ablaginin.Compiler
import ru.nsu.ablaginin.dsl.bricks.Student

class Builder {

    @SneakyThrows
    Student configureStudent(String name) {
        var url = getClass().getClassLoader().getResource("./conf/" + name + ".groovy")
        File file = new File(url.toURI())

        return (Student) Compiler.compile(file, Student.class)
    }
}

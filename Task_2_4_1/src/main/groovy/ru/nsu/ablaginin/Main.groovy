package ru.nsu.ablaginin


import ru.nsu.ablaginin.builder.Builder
import ru.nsu.ablaginin.dsl.Compiler
import ru.nsu.ablaginin.dsl.bricks.Student
import ru.nsu.ablaginin.git.GitWorks
import ru.nsu.ablaginin.helper.FileUtils

// create a new student
student = Compiler.compile(
        new File(getClass().getClassLoader().getResource("./conf/artyom.groovy").toURI()), Student.class
) as Student

// create a temp repo dir
f = File.createTempDir("temp-repo")
f.deleteOnExit()

// clone there
GitWorks.clone(student.url, f, Optional.of("task-1-1-1"))

// test the project
path = f.path.concat("\\Task_1_1_1")
try {
    c = Builder.buildTest(new File(path))
    println c
} catch (Exception e) {
    println e.getMessage()
}

FileUtils.deleteRecursively(f)
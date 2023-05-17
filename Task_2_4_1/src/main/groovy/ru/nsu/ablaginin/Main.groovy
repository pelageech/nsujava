package ru.nsu.ablaginin


import ru.nsu.ablaginin.builder.Builder
import ru.nsu.ablaginin.dsl.Compiler
import ru.nsu.ablaginin.dsl.bricks.Student
import ru.nsu.ablaginin.git.GitWorks
import ru.nsu.ablaginin.helper.FileUtils

b = !true

if (b) {
// create a new student
    var student = Compiler.compile(
            new File(getClass().getClassLoader().getResource("./conf/artyom.groovy").toURI()), Student.class
    ) as Student

// create a temp repo dir
    var f = File.createTempDir("temp-repo")
    f.deleteOnExit()

// clone there
    GitWorks.clone(student.url, f, Optional.of("task-1-2-3"))

// test the project
    var path = f.path.concat("/Task_1_2_3")
    try {
        Builder.jacocoTestReport(new File(path))
    } catch (Exception e) {
        println e.getMessage()
    }
} else {
    try {
        Builder.checkCodeStyle(new File(
                "C:\\Users\\Mi\\AppData\\Local\\Temp\\temp-repo12926346480599657874\\Task_1_2_3"
        ))
    } catch (Exception e) {
        println e.getMessage()
    }
}
// FileUtils.deleteRecursively(f)
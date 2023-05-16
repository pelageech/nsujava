package ru.nsu.ablaginin


import ru.nsu.ablaginin.dsl.bricks.Student

var student = Compiler.compile(
        new File(getClass().getClassLoader().getResource("./conf/artyom.groovy").toURI()), Student.class
) as Student

var f = File.createTempDir("temp-repo")
println f.path
//GitWorks.clone(student.url, f, Optional.empty())

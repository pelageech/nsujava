package ru.nsu.ablaginin


import org.codehaus.groovy.control.CompilerConfiguration
import ru.nsu.ablaginin.dsl.DSL
import ru.nsu.ablaginin.dsl.bricks.Student
import ru.nsu.ablaginin.git.GitWorks

var student = Compiler.compile(
        new File(getClass().getClassLoader().getResource("./conf/artyom.groovy").toURI()), Student.class
) as Student

var git = new GitWorks()

var f = File.createTempDir("temp-repo")
println f.path

git.clone(student.url, f, Optional.empty())

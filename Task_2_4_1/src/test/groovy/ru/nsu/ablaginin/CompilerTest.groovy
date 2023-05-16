package ru.nsu.ablaginin

import org.junit.jupiter.api.Test
import ru.nsu.ablaginin.dsl.Compiler
import ru.nsu.ablaginin.dsl.bricks.Class
import ru.nsu.ablaginin.dsl.bricks.GivenTask
import ru.nsu.ablaginin.dsl.bricks.Group
import ru.nsu.ablaginin.dsl.bricks.Mark
import ru.nsu.ablaginin.dsl.bricks.MarkNum
import ru.nsu.ablaginin.dsl.bricks.Student
import ru.nsu.ablaginin.dsl.bricks.lists.ClassList
import ru.nsu.ablaginin.dsl.bricks.lists.GivenTaskList
import ru.nsu.ablaginin.dsl.bricks.lists.MarkList
import ru.nsu.ablaginin.helper.HelperDSL

import java.time.LocalDate

import static org.junit.jupiter.api.Assertions.*

class CompilerTest {

    @Test
    void compile() {
        Student student = Compiler.compile(new File(
                getClass().getClassLoader().getResource("./conf/artyom.groovy").toURI()
        ), Student.class) as Student

        var mark1 = new Mark(MarkNum.EXCELLENT, LocalDate.parse("11-05-2023", HelperDSL.FORMATTER))
        var mark2 = new Mark(MarkNum.BAD, LocalDate.parse("12-05-2023", HelperDSL.FORMATTER))

        var task1 = new GivenTask(
                "task-1-1-1",
                LocalDate.parse("11-09-2023", HelperDSL.FORMATTER)
        )

        var task2 = new GivenTask(
                "task-1-2-1",
                LocalDate.parse("11-09-2023", HelperDSL.FORMATTER)
        )

        var class1 = new Class(LocalDate.parse("11-05-2023", HelperDSL.FORMATTER), true)

        Student expected = new Student(
                "pelageech",
                "Blaginin Artyom Sergeevich",
                new Group(21215),
                "github.com/pelageech/nsujava",
                new MarkList(),
                new GivenTaskList(),
                new ClassList()
        )
        expected.getMarkList().getMarks().addAll(mark1, mark2)
        expected.getGivenTaskList().getGivenTaskList().addAll(task1, task2)
        expected.getClasses().getClassList().addAll(class1)

        assertEquals(expected, student)
    }

}
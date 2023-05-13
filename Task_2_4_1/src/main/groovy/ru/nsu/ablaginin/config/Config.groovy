package ru.nsu.ablaginin.config

import ru.nsu.ablaginin.dsl.bricks.MarkNum

import java.time.LocalDate

student {
    nickname = "fdfdf"
    name = "kavo"
    url = "fdfdf"
    marks {
        mark {
            mark = MarkNum.EXCELLENT
            date = LocalDate.now()
        }
        mark {
            mark = MarkNum.BAD
            date = LocalDate.now()
        }
    }
    tasks {
        task {
            id = "task-1-1-1"
            date = "11-09-2023"
        }
        task {
            id = "task-1-2-1"
            date = "11-09-2023"
        }
    }
    classes {
        newClass {
            date = LocalDate.now()
            attendance = true
        }
    }
    group {
        number = 12
    }
}
tasks {
    task {
        id = "task-1-1-1"
        name = "heapsort"
        score = 10
    }
    task {
        id = "task-1-2-1"
        name = "stack"
        score = 8
    }
    task {
        id = "task-1-2-2"
        name = "tree"
        score = 10
    }
}
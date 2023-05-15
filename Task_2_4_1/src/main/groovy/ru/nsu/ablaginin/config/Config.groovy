package ru.nsu.ablaginin.config

student {
    nickname = "pelageech"
    name = "Artyom"
    url = "https://github.com/pelageech/nsujava.git"
    marks {
        mark {
            mark = 5
            date = "11-05-2023"
        }
        mark {
            mark = 2
            date = "12-05-2023"
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
            date = "11-05-2023"
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
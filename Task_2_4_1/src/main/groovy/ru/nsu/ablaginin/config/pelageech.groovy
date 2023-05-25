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
            id = "Task_1_1_12"
            date = "11-09-2023"
        }
        task {
            id = "Task_1_2_2"
            date = "11-09-2023"
        }
        task {
            id = "Task_2_1_1"
            date = "11-09-2023"
        }
    }
    classes {
        newClass {
            date = "11-05-2023"
            attendance = true
        }
        newClass {
            date = "11-02-2023"
            attendance = true
        }
        newClass {
            date = "11-11-2022"
            attendance = true
        }
        newClass {
            date = "01-10-2022"
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
        id = "Task_1_2_2"
        name = "Tree"
        score = 8
    }
    task {
        id = "Task_2_1_1"
        name = "Threads"
        score = 6
    }
}
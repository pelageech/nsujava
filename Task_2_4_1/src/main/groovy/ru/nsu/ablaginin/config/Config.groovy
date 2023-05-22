package ru.nsu.ablaginin.config

student {
    nickname = "miqqra"
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
            id = "Task_1_2_3"
            date = "11-09-2023"
        }
        task {
            id = "Task_2_3_1"
            date = "11-09-2023"
        }
        task {
            id = "Task_1_3_1"
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
        id = "Task_1_2_3"
        name = "Graph"
        score = 20
    }
    task {
        id = "Task_2_3_1"
        name = "Snake"
        score = 15
    }
    task {
        id = "Task_1_3_1"
        name = "tree"
        score = 10
    }
}
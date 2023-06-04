package ru.nsu.ablaginin.config

student {
    nickname = "miqqra"
    name = "Michail"
    url = "https://github.com/miqqra/OOP.git"
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
            id = "Task_1_4_1"
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
        id = "Task_1_2_3"
        name = "Graph"
        score = 20
    }
    task {
        id = "Task_1_4_1"
        name = "Student's book"
        score = 15
    }
    task {
        id = "Task_1_3_1"
        name = "Substring"
        score = 10
    }
}
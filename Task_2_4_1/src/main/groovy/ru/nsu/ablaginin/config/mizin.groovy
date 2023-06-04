package ru.nsu.ablaginin.config

student {
    nickname = "destroyer"
    name = "Maxim"
    url = "https://github.com/MIZINCHIK/OOP.git"
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
            id = "Task_1_1_1"
            date = "11-09-2023"
        }
        task {
            id = "Task_1_2_1"
            date = "11-09-2023"
        }
        task {
            id = "Task_1_5_1"
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
        id = "Task_1_1_1"
        name = "Heapsort"
        score = 13
    }
    task {
        id = "Task_1_2_1"
        name = "Tree"
        score = 15
    }
    task {
        id = "Task_1_5_1"
        name = "Calculator"
        score = 100
    }
}
package ru.nsu.ablaginin.config

import ru.nsu.ablaginin.bricks.MarkNum

import java.time.LocalDate

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
        id = "math"
        date = LocalDate.now()
    }
    task {
        id = "jiraf"
        date = LocalDate.now()
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
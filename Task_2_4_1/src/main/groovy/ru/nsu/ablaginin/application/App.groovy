package ru.nsu.ablaginin.application

import ru.nsu.ablaginin.builder.Builder
import ru.nsu.ablaginin.dsl.Dsl
import ru.nsu.ablaginin.dsl.bricks.Class
import ru.nsu.ablaginin.git.GitWorks
import ru.nsu.ablaginin.html.HtmlTable

import java.nio.file.Path

class App {
    public static final List<String> fieldsTable = List.of("nickname", "task", "build", "style", "javadoc", "tests", "skipped", "failures", "errors", "time", "score")
    public static final List<String> fieldsAttendance = List.of("nickname", "date", "attendance")

    static HtmlTable testConfig(File tempRepo, Dsl dsl, Boolean styleCheck, Boolean javadocCheck) {
        Path p = tempRepo.toPath()

        HtmlTable localTable = new HtmlTable()
        for (v in fieldsTable) {
            localTable.th.addTh(v)
        }

        var tasks = dsl.getTaskMap()
        var givenTasks = dsl.getStudent().getGivenTaskList().getGivenTaskList()

        for (t in givenTasks) {
            var currentTask = tasks.getTaskMap().get(t.id)
            var nick = dsl.student.nickname
            var task = t.id
            var build = "N"
            var style = "-"
            var javadoc = "-"
            var tests = "-"
            var skipped = "-"
            var failures = "-"
            var errors = "-"
            var time = "-"
            var score = "-"

            try {
                var taskFile = new File(p.toString().concat("/" + t.getId()))

                // tests START
                Builder.buildTest(taskFile)
                var map = Builder.getJacocoTestReport(taskFile)

                Double maxScore = -1
                if (currentTask != null) {
                    maxScore = currentTask.score
                    task += " ($currentTask.name)"
                }

                build = "Y"
                tests = map.get("tests")
                skipped = map.get("skipped")
                failures = map.get("failures")
                errors = map.get("errors")
                time = map.get("time")
                if (maxScore != -1) {
                    score = String.format("%.1f",
                            maxScore * (1 - (Double.parseDouble(failures) + Double.parseDouble(errors)) /
                            (Double.parseDouble(tests) - Double.parseDouble(skipped)))
                    ).replace(',', '.') + "/" + maxScore
                }
                // tests END
                // style STARTS
                if (styleCheck) {
                    style = "Y"
                } else {
                    style = "N"
                }
                // style ENDS

                // javadoc STARTS
                if (javadocCheck) {
                    if (Builder.buildJavadoc(taskFile)) {
                        javadoc = "Y"
                    } else {
                        javadoc = "N"
                    }
                }
                // javadoc ENDS

            } catch (Exception e) {
                println "Task " + t.getId() + ": " + e.getMessage()
            }
            // table building STARTS
            HtmlTable.Tr tr = new HtmlTable.Tr()
            tr.addTd(nick) // nick
            tr.addTd(task) // task
            tr.addTd(build) // build
            tr.addTd(style) // style
            tr.addTd(javadoc) // javadoc
            tr.addTd(tests) // tests
            tr.addTd(skipped)
            tr.addTd(failures)
            tr.addTd(errors)
            tr.addTd(time)
            tr.addTd(score)
            localTable.tbody.add(tr)
            // table building ENDS
        }

        println("Tests complete!")
        return localTable
    }

    static HtmlTable attendance(File repository, String nickname, List<Class> classList) {
        HtmlTable localTable = new HtmlTable()
        for (v in fieldsAttendance) {
            localTable.th.addTh(v)
        }

        for (c in classList) {
            var date = c.date
            HtmlTable.Tr tr = new HtmlTable.Tr()
            tr.addTd(nickname)
            tr.addTd(date.toString())

            try {
                var att = GitWorks.checkAttendance(repository, date)
                tr.addTd(att.toString())
            } catch (Exception e) {
                println e.getMessage()
                tr.addTd("?")
            }

            localTable.tbody.add(tr)
        }
        return localTable
    }
}

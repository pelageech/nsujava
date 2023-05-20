package ru.nsu.ablaginin

import picocli.CommandLine
import ru.nsu.ablaginin.builder.Builder
import ru.nsu.ablaginin.dsl.Compiler
import ru.nsu.ablaginin.dsl.DSL
import ru.nsu.ablaginin.git.GitWorks
import ru.nsu.ablaginin.helper.FileUtils
import ru.nsu.ablaginin.html.HTMLTable

import java.nio.file.Files
import java.nio.file.Path
import java.util.concurrent.Callable

@CommandLine.Command(name="labcheck", mixinStandardHelpOptions = true)
class Main implements Callable<Integer> {
    private static final List<String> fields = List.of("nickname", "task", "build", "style", "tests", "skipped", "failures", "errors", "time", "score")

    @CommandLine.Option(names = ["--config-dir"], description = "directory with all the config files")
    File configDir

    @CommandLine.Option(names = ["--config-file"], description = "config file")
    File config

    @CommandLine.Option(names = ["-b", "--branch"], description = "branch", defaultValue = "master")
    String branch

    HTMLTable testConfig(File dslConfig) {
        if (!dslConfig.exists()) {
            throw new FileNotFoundException("File doesn't exist: " + dslConfig.getCanonicalPath())
        }

        println("File " + dslConfig.getCanonicalPath() + " has got!")
        DSL dsl = Compiler.compile(dslConfig, DSL.class) as DSL
        if (dsl == null) {
            throw new IllegalStateException("Student info wasn't parsed. Skipping...")
        }

        Path p = Files.createTempDirectory("temp-repo")
        File tempRepo = p.toFile()
        tempRepo.deleteOnExit()

        GitWorks.clone(dsl.student.getUrl(), tempRepo, Optional.of(branch))

        HTMLTable localTable = new HTMLTable()
        for (v in fields) {
            localTable.th.addTh(v)
        }

        var givenTasks = dsl.getStudent().getGivenTaskList().getGivenTaskList()

        for (t in givenTasks) {
            var nick = dsl.student.nickname
            var task = t.id
            var build = "N"
            var style = "N"
            var tests = "-"
            var skipped = "-"
            var failures = "-"
            var errors = "-"
            var time = "-"
            var score = "0"

            try {
                var taskFile = new File(p.toString().concat("/" + t.getId()))

                // tests START
                Builder.buildTest(taskFile)
                var map = Builder.getJacocoTestReport(taskFile)
                build = "Y"
                tests = map.get("tests")
                skipped = map.get("skipped")
                failures = map.get("failures")
                errors = map.get("errors")
                time = map.get("time")
                score = "10"
                // tests END
                // style STARTS

                // style ENDS

            } catch (FileNotFoundException e) {
                println "Task " + t.getId() + ": " + e.getMessage()
            }
            // table building STARTS
            HTMLTable.Tr tr = new HTMLTable.Tr()
            tr.addTd(nick) // nick
            tr.addTd(task) // task
            tr.addTd(build) // build
            tr.addTd(style) // style
            tr.addTd(tests) // tests
            tr.addTd(skipped)
            tr.addTd(failures)
            tr.addTd(errors)
            tr.addTd(time)
            tr.addTd(score)
            localTable.tbody.add(tr)
            // table building ENDS
        }

        if (!FileUtils.deleteRecursively(tempRepo)) {
            println("Temp file has not been deleted: " + tempRepo.path)
        }
        println("Tests complete!")
        return localTable
    }

    static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args)
        System.exit(exitCode)
    }

    @Override
    Integer call() throws Exception {
        HTMLTable globTable = new HTMLTable()
        for (v in fields) {
            globTable.th.addTh(v)
        }

        if (configDir != null) {
            for (f in configDir.listFiles()) {
                try {
                    globTable.merge(testConfig(f))
                } catch (Exception e) {
                    println "Failed configure: " + f.getPath() + " " + e.getMessage()
                }
            }
        }
        if (config != null) {
            globTable.merge(testConfig(config))
        }

        File file = Files.createTempFile("temp", ".html").toFile()
        OutputStream os = new BufferedOutputStream(new FileOutputStream(file))
        os.write(globTable.build().getBytes())
        os.flush()
        return 0
    }
}
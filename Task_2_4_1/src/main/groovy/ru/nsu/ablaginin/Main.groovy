package ru.nsu.ablaginin

import picocli.CommandLine
import ru.nsu.ablaginin.application.App
import ru.nsu.ablaginin.dsl.Compiler
import ru.nsu.ablaginin.dsl.DSL
import ru.nsu.ablaginin.git.GitWorks
import ru.nsu.ablaginin.helper.FileUtils
import ru.nsu.ablaginin.html.HTMLMake
import ru.nsu.ablaginin.html.HTMLTable

import java.nio.file.Files
import java.nio.file.Path
import java.util.concurrent.Callable

@CommandLine.Command(name="labcheck", mixinStandardHelpOptions = true)
class Main implements Callable<Integer> {

    @CommandLine.Option(names = ["--config-dir"], description = "directory with all the config files")
    File configDir

    @CommandLine.Option(names = ["--config-file"], description = "config file")
    File config

    @CommandLine.Option(names = ["-B", "--branch"], description = "branch", defaultValue = "master")
    String branch

    @CommandLine.Option(names = ["-s", "--style"], description = "is check style needed")
    Boolean style = false

    @CommandLine.Option(names = ["-d", "--javadoc"], description = "is javadoc gen needed")
    Boolean javadoc = false

    @CommandLine.Option(names = ['-a', '--attendance'], description = 'should consider an attendance?')
    Boolean attendance = false

    static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args)
        System.exit(exitCode)
    }

    @Override
    Integer call() throws Exception {
        HTMLMake make = new HTMLMake()
        HTMLTable globTable = new HTMLTable()
        for (v in App.fieldsTable) {
            globTable.th.addTh(v)
        }

        HTMLTable attendanceTable = new HTMLTable()
        for (v in App.fieldsAttendance) {
            attendanceTable.th.addTh(v)
        }

        // iterate through directory of configs
        if (configDir != null) {
            for (f in configDir.listFiles()) {
                try {
                    buildProject(f, globTable, attendanceTable)
                } catch (Exception e) {
                    println "Failed configure: " + f.getPath() + " " + e.getMessage()
                }
            }
        }

        // check only one config
        if (config != null) {
            if (!config.exists()) {
                throw new FileNotFoundException("File doesn't exist: " + config.getCanonicalPath())
            }
            println("File " + config.getCanonicalPath() + " has got!")
            try {
                buildProject(config, globTable, attendanceTable)
            } catch (Exception e) {
                println "Failed configure: " + config.getPath() + " " + e.getMessage()
            }
        }

        make.addBuilder(globTable)
        make.addBuilder(attendanceTable)

        // put the table into file
        File file = Files.createTempFile("temp", ".html").toFile()
        try (OutputStream os = new BufferedOutputStream(new FileOutputStream(file))) {
            os.write(make.build().getBytes())
            os.flush()
        } catch (IOException e) {
            println "Failed writing to file " + file.getPath() + " " + e.getMessage()
        }
        println "Your report is in " + file.getPath()
        return 0
    }

    private void buildProject(File f, HTMLTable globTable, HTMLTable attendanceTable) {
        DSL dsl = Compiler.compile(f, DSL.class) as DSL
        if (dsl == null) {
            throw new IllegalStateException("Student info wasn't parsed. Skipping...")
        }
        Path p = Files.createTempDirectory("temp-repo")
        File tempRepo = p.toFile()
        tempRepo.deleteOnExit()

        GitWorks.clone(dsl.student.getUrl(), tempRepo, Optional.of(branch))
        globTable.merge(App.testConfig(tempRepo, dsl, branch, style, javadoc))

        if (attendance) {
            attendanceTable.merge(App.attendance(
                    tempRepo, dsl.student.nickname, dsl.student.classes.classList
            ))
        }

        FileUtils.deleteRecursively(tempRepo)
    }
}
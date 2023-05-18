package ru.nsu.ablaginin

import picocli.CommandLine
import ru.nsu.ablaginin.builder.Builder
import ru.nsu.ablaginin.dsl.Compiler
import ru.nsu.ablaginin.dsl.DSL
import ru.nsu.ablaginin.git.GitWorks
import ru.nsu.ablaginin.helper.FileUtils

import java.nio.file.Files
import java.nio.file.Path
import java.util.concurrent.Callable

@CommandLine.Command(name="labcheck", mixinStandardHelpOptions = true)
class Main implements Callable<Integer> {
    @CommandLine.Option(names = ["--config-dir"], description = "directory with all the config files")
    File configDir

    @CommandLine.Option(names = ["--config-file"], description = "config file")
    File config

    @CommandLine.Option(names = ["-b", "--branch"], description = "branch", defaultValue = "master")
    String branch

    Integer testConfig(File dslConfig) {
        if (!dslConfig.exists()) {
            println("File doesn't exist: " + dslConfig.getCanonicalPath())
            return 1
        }

        try {
            println("File " + dslConfig.getCanonicalPath() + " has got!")
            DSL dsl = Compiler.compile(dslConfig, DSL.class) as DSL
            if (dsl == null) {
                println("Student info wasn't parsed. Skipping...")
                return 2
            }

            Path p = Files.createTempDirectory("temp-repo")
            File tempRepo = p.toFile()
            tempRepo.deleteOnExit()

            GitWorks.clone(dsl.student.getUrl(), tempRepo, Optional.of(branch))
            Builder.checkCodeStyle(new File(p.toString().concat("/Task_1_1_1")))
            if (!FileUtils.deleteRecursively(tempRepo)) {
                println("Temp file has not been deleted: " + tempRepo.path)
            }
            println("Tests complete!")
        } catch (Exception e) {
            println(e.getMessage())
            return 3
        }
        return 0
    }

    static void main(String[] args) {
        int exitCode = new CommandLine(new MainJava()).execute(args)
        System.exit(exitCode)
    }

    @Override
    Integer call() throws Exception {
        if (configDir != null) {
            for (f in configDir.listFiles()) {
                testConfig(f)
            }
        }
        if (config != null) {
            testConfig(config)
        }
        return 0
    }
}
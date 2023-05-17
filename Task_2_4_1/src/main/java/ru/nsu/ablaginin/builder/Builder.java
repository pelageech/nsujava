package ru.nsu.ablaginin.builder;

import lombok.SneakyThrows;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;
import org.gradle.tooling.model.GradleTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;

public class Builder {

    @SneakyThrows
    public static void build(File projectDir, String task) {
        if (!projectDir.exists()) {
            throw new FileNotFoundException("file " + projectDir.getPath() + " not found");
        }

        try (ProjectConnection conn = GradleConnector.newConnector().forProjectDirectory(projectDir).connect()) {
            conn.newBuild().forTasks(task).run();
        }
    }

    public static boolean buildTest(File projectDir) {
        try {
            build(projectDir, "test");
        } catch (Exception e) {
            System.out.println("Failed to build");
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @SneakyThrows
    public static void checkCodeStyle(File projectDir) {
        build(projectDir, "checkstyleMain");

        var directory = new File("./reports/$name/$lab/codeCoverage");
        var dir = Path.of("./reports/$name/$lab/codeCoverage");
        var origin = Path.of("./repos/$name/$lab/build/jacocoHtml");

    }

    @SneakyThrows
    public static void jacocoTestReport(File projectDir) {
        build(projectDir, "jacocoTestReport");
    }
}

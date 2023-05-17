package ru.nsu.ablaginin.builder;

import lombok.SneakyThrows;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProgressListener;
import org.gradle.tooling.ProjectConnection;

import java.io.File;
import java.io.FileNotFoundException;

public class Builder {

    @SneakyThrows
    public static void build(File projectDir, String task) {
        if (!projectDir.exists()) {
            throw new FileNotFoundException("file " + projectDir.getPath() + " not found");
        }

        try (var conn = GradleConnector.newConnector().forProjectDirectory(projectDir).connect()) {
            conn.newBuild().forTasks(task).addProgressListener(
                    (ProgressListener) event -> System.out.println(event.getDescription())
            ).run();
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
    public static boolean checkCodeStyle(File projectDir) {
        try {
            build(projectDir, "checkstyleMain");
        } catch (Exception e) {
            System.out.println("Failed to build");
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @SneakyThrows
    public static void jacocoTestReport(File projectDir) {
        build(projectDir, "jacocoTestReport");
    }
}

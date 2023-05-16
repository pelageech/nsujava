package ru.nsu.ablaginin.builder;

import lombok.SneakyThrows;
import org.gradle.tooling.BuildException;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;

import java.io.File;
import java.io.FileNotFoundException;

public class Builder {
    @SneakyThrows
    public static boolean buildTest(File projectDir) {
        if (!projectDir.exists()) {
            throw new FileNotFoundException("file " + projectDir.getPath() + " not found");
        }

        try (ProjectConnection conn = GradleConnector.newConnector().forProjectDirectory(projectDir).connect()) {
            conn.newBuild().forTasks("test").run();
        } catch (BuildException e) {
            System.out.println("Failed to build");
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }
}

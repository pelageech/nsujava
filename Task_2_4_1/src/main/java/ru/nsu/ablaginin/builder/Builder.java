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
    public static void checkCodeStyle(File projectDir) {

        // Create a Gradle connector and connect to the project
        GradleConnector connector = GradleConnector.newConnector();
        connector.forProjectDirectory(projectDir);

        try (ProjectConnection connection = connector.connect()) {
            // Get the build environment and project information
//            BuildEnvironment buildEnvironment = connection.getModel(BuildEnvironment.class);
//            BasicGradleProject project = connection.getModel(BasicGradleProject.class);

            // Add the Checkstyle plugin and run the Checkstyle task
            connection.newBuild()
                    .withArguments(
                            "--init-script",
                            createInitScript(),
                            "checkstyleMain"
                    )
                    .setStandardOutput(System.out)
                    .setStandardError(System.err)
                    .run();
        }
    }

    private static String createInitScript() {
        return "initscript {" +
                "  repositories {" +
                "    mavenCentral()" +
                "  }" +
                "  dependencies {" +
                "    classpath 'com.puppycrawl.tools:checkstyle:9.2'" +
                "  }" +
                "}" +
                "allprojects {" +
                "  apply plugin: com.puppycrawl.tools.checkstyle.CheckstylePlugin" +
                "  checkstyle {" +
                "    toolVersion = '9.2'" +
                "    configFile = file('config/checkstyle/checkstyle.xml')" +
                "  }" +
                "}";
    }

    @SneakyThrows
    public static void jacocoTestReport(File projectDir) {
        build(projectDir, "jacocoTestReport");
    }
}

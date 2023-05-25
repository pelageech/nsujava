package ru.nsu.ablaginin.builder;

import lombok.SneakyThrows;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProgressListener;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.*;

public class Builder {
    private static final List<String> fields = List.of(
            "name", "tests", "skipped", "failures", "errors", "timestamp", "time"
    );

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
    public static boolean buildJavadoc(File projectDir) {
        try {
            build(projectDir, "javadoc");
        } catch (Exception e) {
            System.out.println("Failed to build");
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @SneakyThrows
    public static Map<String, String> getJacocoTestReport(File projectDir) {
        File testDir = new File(projectDir.getPath() + "/build/test-results/test");
        if (!testDir.exists()) {
            throw new FileNotFoundException(testDir.getAbsolutePath());
        }

        var files = testDir.listFiles(pathname -> !pathname.isDirectory());
        if (files == null || files.length == 0) {
            throw new IllegalStateException("there's no files to check");
        }

        Map<String, String> table = new HashMap<>();

        for (var f : files) {
            try {
                var docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                var document = docBuilder.parse(f);

                Node root = document.getDocumentElement();

                var attrs = root.getAttributes();

                for (var field : fields) {
                    table.put(field, attrs.getNamedItem(field).getNodeValue());
                }

            } catch (Exception e) {
                System.out.println("File " + f.getPath() + ": " + e.getMessage());
            }
        }

        return table;
    }
}

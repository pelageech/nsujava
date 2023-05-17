package ru.nsu.ablaginin.builder;

import org.junit.jupiter.api.Test;
import ru.nsu.ablaginin.git.GitWorks;
import ru.nsu.ablaginin.helper.FileUtils;

import java.io.File;
import java.nio.file.Files;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class BuilderTest {
    @Test
    public void builder() {
        try {
            File f = new File(Files.createTempDirectory("temp-repo").toUri());
            GitWorks.clone(
                    "https://github.com/pelageech/nsujava.git",
                    f,
                    Optional.of("task-1-1-1")
            );
            assertTrue(Builder.buildTest(new File(f.getPath() + "/Task_1_1_1")));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }
}

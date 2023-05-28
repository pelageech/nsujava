package ru.nsu.ablaginin.git;

import org.junit.jupiter.api.Test;
import ru.nsu.ablaginin.helper.FileUtils;

import java.io.File;
import java.nio.file.Files;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Tests for GitWorks.
 */
public class GitWorksTest {
    @Test
    public void cloneTest() {
        try {
            File f = new File(Files.createTempDirectory("temp-repo").toUri());
            GitWorks.clone(
                    "https://github.com/boltdb/bolt.git",
                    f,
                    Optional.empty()
            );
            assertTrue(FileUtils.deleteRecursively(f));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }
}

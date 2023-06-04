package ru.nsu.ablaginin.helper;

import java.io.File;
import java.util.Objects;

/**
 * Tools for working with files.
 */
public class FileUtils {

    /**
     * Delete a file and all the files inside if folder were given.
     *
     * @param f file to delete
     * @return true if the file were successfully deleted
     */
    public static boolean deleteRecursively(File f) {
        if (f.isDirectory()) {
            for (var d : Objects.requireNonNull(f.listFiles())) {
                deleteRecursively(d);
            }
        }
        return f.delete();
    }
}

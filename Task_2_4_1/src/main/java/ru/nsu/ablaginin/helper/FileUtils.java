package ru.nsu.ablaginin.helper;

import java.io.File;
import java.util.Objects;

public class FileUtils {

    public static boolean deleteRecursively(File f) {
        if (f.isDirectory()) {
            for (var d : Objects.requireNonNull(f.listFiles())) {
                deleteRecursively(d);
            }
        }
        return f.delete();
    }
}

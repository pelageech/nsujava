package ru.nsu.ablaginin.git

import lombok.Cleanup
import lombok.SneakyThrows
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.storage.file.FileRepositoryBuilder

import java.nio.file.Files
import java.nio.file.Paths

import java.nio.file.Path

class GitWorks {

    static Git init(String name) {
        Path repoPath = Paths.get("./repos/"+name);

        return Git.init().setDirectory(repoPath.toFile()).call()
    }

    static Git open(String name) {
        Path repoPath = Paths.get("./repos/"+name);

        return Git.open(repoPath.toFile())
    }

    @SneakyThrows
    void pull(String name, String branch) {

    }

    @SneakyThrows
    void clone(String name) {

    }

    boolean logStudentsAttendance(Date monday, Date sunday, String repo) {

    }
}

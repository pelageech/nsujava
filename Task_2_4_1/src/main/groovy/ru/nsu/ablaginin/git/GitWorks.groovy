package ru.nsu.ablaginin.git

import lombok.Cleanup
import lombok.SneakyThrows
import org.eclipse.jgit.api.CloneCommand
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.ProgressMonitor
import org.eclipse.jgit.lib.TextProgressMonitor

import java.nio.file.Path
import java.nio.file.Paths

class GitWorks {

    public static final String DEFAULT_BRANCH = "master";

    static Git init(String name) {
        Path repoPath = Paths.get("./repos/"+name);

        return Git.init().setDirectory(repoPath.toFile()).call()
    }

    static Git open(String name) {
        Path repoPath = Paths.get("./repos/"+name);

        return Git.open(repoPath.toFile())
    }

    @SneakyThrows
    void pull(String remote, String name, String branch) {

    }

    @SneakyThrows
    void clone(String remotePath, File localPath) {
        try {
            CloneCommand cloneCommand = new CloneCommand();
            cloneCommand.setURI(remotePath);
            cloneCommand.setProgressMonitor(new TextProgressMonitor())
            cloneCommand.setDirectory(localPath);
            cloneCommand.setBranch(DEFAULT_BRANCH);
            cloneCommand.call();
        } catch (Exception e) {
            println e.getMessage()
        }
    }

    boolean logStudentsAttendance(Date monday, Date sunday, String repo) {

    }
}

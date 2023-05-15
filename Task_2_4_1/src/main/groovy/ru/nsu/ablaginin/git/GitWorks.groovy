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

    @SneakyThrows
    void pull(String remote, String name, String branch) {

    }

    @SneakyThrows
    void clone(String remotePath, File localPath, Optional<String> branch) {
        var remoteBranch = branch.isPresent()
                ? branch.get()
                : DEFAULT_BRANCH;
        try {
            CloneCommand cloneCommand = new CloneCommand();
            cloneCommand.setURI(remotePath);
            cloneCommand.setProgressMonitor(new TextProgressMonitor())
            cloneCommand.setDirectory(localPath);
            cloneCommand.setBranch(remoteBranch);
            cloneCommand.call();
        } catch (Exception e) {
            println e.getMessage()
        }
    }

    boolean logStudentsAttendance(Date monday, Date sunday, String repo) {

    }
}

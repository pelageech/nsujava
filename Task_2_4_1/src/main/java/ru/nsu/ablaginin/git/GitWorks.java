package ru.nsu.ablaginin.git;

import lombok.Cleanup;
import lombok.SneakyThrows;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.revwalk.filter.CommitTimeRevFilter;

import java.io.File;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

/**
 * GitWorks is a library of methods for working with git.
 */
public class GitWorks {
    public static final String DEFAULT_BRANCH = "master";

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static void clone(
            String remotePath,
            File localFile,
            Optional<String> branch
    ) throws GitAPIException {
        String remoteBranch = branch.orElse(DEFAULT_BRANCH);

        CloneCommand cloneCommand = new CloneCommand();
        cloneCommand.setURI(remotePath);
        cloneCommand.setProgressMonitor(new TextProgressMonitor());
        cloneCommand.setDirectory(localFile);
        cloneCommand.setBranch(remoteBranch);
        //noinspection EmptyTryBlock
        try (var ignored = cloneCommand.call()) {

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @SneakyThrows
    public static boolean checkAttendance(
            File repositoryDir,
            LocalDate date
    ) {
        var startDate = date.minusDays(7);

        @Cleanup Git git = Git.open(repositoryDir);
        var between = CommitTimeRevFilter.between(Date.valueOf(startDate), Date.valueOf(date));
        var revCommit = git.log().setRevFilter(between).call();
        return revCommit.iterator().hasNext();
    }
}

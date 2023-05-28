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

    /**
     * Clones a repository to the particular folder.
     *
     * @param remotePath remote, e.g. GitHub, URL
     * @param localFile where to clone
     * @param branch optional branch
     * @throws GitAPIException git api exception
     */
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

        try (var ignored = cloneCommand.call()) {
            System.out.print(""); // reviewdog requires something here D:
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    /**
     * Checks if the student were on the classes.
     *
     * @param repositoryDir repository
     * @param date date to check
     * @return if the student were present on the given date
     */
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

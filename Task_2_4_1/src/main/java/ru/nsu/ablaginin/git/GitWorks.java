package ru.nsu.ablaginin.git;

import lombok.Cleanup;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.TextProgressMonitor;

import java.io.File;
import java.util.Optional;

/**
 * GitWorks is a library of methods for working with git.
 */
public class GitWorks {
    public static final String DEFAULT_BRANCH = "master";

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static void clone(String remotePath, File localFile, Optional<String> branch) {
        String remoteBranch = branch.orElse(DEFAULT_BRANCH);

        CloneCommand cloneCommand = new CloneCommand();
        cloneCommand.setURI(remotePath);
        cloneCommand.setProgressMonitor(new TextProgressMonitor());
        cloneCommand.setDirectory(localFile);
        cloneCommand.setBranch(remoteBranch);

        //noinspection EmptyTryBlock
        try (var git = cloneCommand.call()) {

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static void clone(String remotePath, String localPath, Optional<String> branch) {
        clone(remotePath, new File(localPath), branch);
    }
}

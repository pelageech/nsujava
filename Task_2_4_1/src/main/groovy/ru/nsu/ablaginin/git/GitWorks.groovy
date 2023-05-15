package ru.nsu.ablaginin.git


import org.eclipse.jgit.api.CloneCommand
import org.eclipse.jgit.lib.TextProgressMonitor

class GitWorks {

    public static final String DEFAULT_BRANCH = "master";

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
}

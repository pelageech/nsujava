package ru.nsu.ablaginin.git

import lombok.Cleanup
import lombok.SneakyThrows
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.api.errors.JGitInternalException
import org.eclipse.jgit.api.errors.RefNotFoundException
import org.eclipse.jgit.revwalk.filter.CommitTimeRevFilter
import org.eclipse.jgit.transport.URIish
import ru.nsu.ablaginin.Compiler
import ru.nsu.ablaginin.dsl.bricks.Student

import java.nio.file.Files
import java.nio.file.Path

class GitWorks {

    @SneakyThrows
    void pull(String repo, String branch) {

    }

    @SneakyThrows
    void clone(String repo) {

    }

    boolean logStudentsAttendance(Date monday, Date sunday, String repo) {

    }
}

package com.pluxurydolo

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.TaskAction

import javax.inject.Inject

@CacheableTask
class VersionTask extends DefaultTask {
    private final Project project

    @Inject
    VersionTask(Project project) {
        this.project = project
    }

    @TaskAction
    void setVersion() {
        def branch = gitBranch()

        if (branch && !branch.isEmpty()) {
            project.version = branch
            project.logger.lifecycle('dvxf Setting project version: {}', project.version)
        } else {
            project.logger.warn('zamd Unable to determine Git branch. Keeping existing version: <{}>', project.version)
        }
    }

    private static String gitBranch() {
        Process process = new ProcessBuilder('git', 'rev-parse', '--abbrev-ref', 'HEAD')
                .redirectErrorStream(true)
                .start()

        String branch = process.text.trim()
        process.waitFor()

        return branch
    }
}

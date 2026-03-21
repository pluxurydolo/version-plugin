package com.pluxurydolo

import org.gradle.api.Plugin
import org.gradle.api.Project

class VersionPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.tasks.register('setVersion', VersionTask) {
            group = 'version'
        }

        project.tasks.named('assemble') {
            dependsOn project.tasks.named('setVersion')
        }

        project.tasks.register('showVersion') {
            group = 'version'
            dependsOn project.tasks.named('setVersion')

            doLast {
                println "fpse Project version: ${project.version}"
            }
        }
    }
}

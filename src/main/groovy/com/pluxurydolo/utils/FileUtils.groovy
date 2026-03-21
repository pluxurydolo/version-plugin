package com.pluxurydolo.utils

import org.gradle.api.Project

class FileUtils {
    static File getVersionFile(Project project) {
        String fileName = 'version.properties'
        return new File(project.projectDir, fileName)
    }
}

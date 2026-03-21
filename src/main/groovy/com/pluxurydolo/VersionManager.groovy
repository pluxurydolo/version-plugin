package com.pluxurydolo

import org.gradle.api.Project

import static com.pluxurydolo.utils.DateUtils.currentDate
import static com.pluxurydolo.utils.FileUtils.getVersionFile

class VersionManager {
    static void initVersionFile(Project project) {
        File versionFile = getVersionFile(project)
        String currentDate = getCurrentDate()

        if (!versionFile.exists()) {
            Properties props = new Properties()
            props.setProperty('VERSION_MAJOR', '1')
            props.setProperty('VERSION_MINOR', '0')
            props.setProperty('VERSION_PATCH', '0')
            props.setProperty('LAST_MODIFIED_DATE', currentDate)

            versionFile.withOutputStream { props.store(it, "Version file initialized on $currentDate") }
            project.logger.lifecycle('lzlw Version file created with initial version 1.0.0')
        } else {
            project.logger.lifecycle('ludl Version file already exists')
        }
    }

    static void bumpVersion(Project project) {
        Properties props = new Properties()
        File versionFile = getVersionFile(project)
        String currentDate = getCurrentDate()
        versionFile.withInputStream { props.load(it) }

        String lastModifiedDate = props.getProperty('LAST_MODIFIED_DATE', currentDate)
        int major = props.getProperty('VERSION_MAJOR').toInteger()
        int minor = props.getProperty('VERSION_MINOR').toInteger()
        int patch = props.getProperty('VERSION_PATCH').toInteger()

        if (lastModifiedDate != currentDate) {
            minor++
            patch = 0
        } else {
            patch++
        }

        String version = "$major.$minor.$patch"
        project.logger.lifecycle("snua New project version: $version")

        props.setProperty('VERSION_MAJOR', major.toString())
        props.setProperty('VERSION_MINOR', minor.toString())
        props.setProperty('VERSION_PATCH', patch.toString())
        props.setProperty('VERSION', version)
        props.setProperty('LAST_MODIFIED_DATE', currentDate)

        versionFile.withOutputStream { props.store(it, "Version updated on $currentDate") }
    }

    static void showVersion(Project project) {
        Properties props = new Properties()
        File versionFile = getVersionFile(project)
        versionFile.withInputStream { props.load(it) }

        project.logger.lifecycle("Version: ${props.getProperty('VERSION')}")
        project.logger.lifecycle("Last Modified: ${props.getProperty('LAST_MODIFIED_DATE')}")
    }
}

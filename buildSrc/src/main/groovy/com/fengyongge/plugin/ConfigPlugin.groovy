package com.fengyongge.plugin;

import org.gradle.api.Plugin
import org.gradle.api.Project

class ConfigPlugin implements Plugin<Project> {
    @Override
     void apply(Project project) {

        def extension = project.extensions.create('hencoder', Extension)
        project.afterEvaluate {
            println "Hi ${extension.name}"
        }

    }
}
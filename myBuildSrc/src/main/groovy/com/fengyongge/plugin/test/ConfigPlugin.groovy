//package com.fengyongge.plugin.test
//
//import org.gradle.api.Plugin
//import org.gradle.api.Project
//
//class ConfigPlugin implements Plugin<Project> {
//    @Override
//     void apply(Project project) {
//
//        def extension = project.extensions.create('hencoder', Extension)
//        project.afterEvaluate {
//            println "Hi ${extension.name}"
//        }
//
//        def transform = new CustomTransform()
//        def baseExtension = project.extensions.getByType(BaseExtension)
//        baseExtension.registerTransform(transform)
//
//    }
//}
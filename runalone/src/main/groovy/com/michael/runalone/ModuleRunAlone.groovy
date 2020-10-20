package com.michael.runalone

import org.gradle.api.Plugin
import org.gradle.api.Project

class ModuleRunAlone implements Plugin<Project> {

    void apply(Project project) {
        def extension = project.extensions.create('componentFeatures', RunAloneExtension)
        if (extension.runAlone == null) {
            throw new RuntimeException("you should set 'componentFeatures{runAlone=false/true}' in " + module + "'s build.gradle")
        }

        boolean isRunAlone = extension.runAlone
        if (isRunAlone) {
            project.apply plugin: 'com.android.application'
            project.apply plugin: 'com.hm.plugin.lifecycle'//Android-AppLifecycleMgr注册组件生命周期的初始化
            project.android.sourceSets {
                main {
                    manifest.srcFile 'src/main/runalone/AndroidManifest.xml'
                }
            }
            System.out.println("apply plugin is " + 'com.android.application')
            //compileComponents(assembleTask, project)
            //project.android.registerTransform(new ComCodeTransform(project))
        } else {
            project.apply plugin: 'com.android.library'
            project.android.sourceSets {
                main {
                    manifest.srcFile 'src/main/AndroidManifest.xml'
                    java {
                        exclude '*runalone'
                    }
                }
            }
            System.out.println("apply plugin is " + 'com.android.library')
        }
    }
}
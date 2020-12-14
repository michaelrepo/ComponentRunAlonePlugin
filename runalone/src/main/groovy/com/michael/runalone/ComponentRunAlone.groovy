package com.michael.runalone

import org.gradle.api.Plugin
import org.gradle.api.Project

class ComponentRunAlone implements Plugin<Project> {

    void apply(Project project) {

        try {
            boolean isRunAlone = project.rootProject.property("${project.name}_runalone")

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
        } catch (Exception e) {
            throw new RuntimeException("请在根目录的gradle.properties添加:${project.name}_runalone = false/ture ")
        }

    }
}
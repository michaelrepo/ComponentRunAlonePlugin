package com.michael.runalone

import org.gradle.api.Plugin
import org.gradle.api.Project

class ComponentRunAlone implements Plugin<Project> {

    void apply(Project project) {
        try {
            boolean isRunAlone = project.ext.runAlone

            if (isRunAlone) {
                project.apply plugin: 'com.android.application'
                project.apply plugin: 'com.hm.plugin.lifecycle'//Android-AppLifecycleMgr注册组件生命周期的初始化
                project.android.sourceSets {
                    main {
                        manifest.srcFile 'src/main/runalone/AndroidManifest.xml'
                    }
                }
                project.android.sourceSets.defaultConfig {
                    applicationId "com.wawj.${project.name}"
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
            throw new RuntimeException("请确认您是否在${project.name}组件的build.gradle首行添加了“ ext{runAlone=false/true} ”，注意：一定要添加在文件首行")
        }

    }
}
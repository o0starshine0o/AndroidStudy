package com.abelhu.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 作为插件的真正入口
 */
class MyPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        // 注意，第一个buildScriptExtension是自定义的DSL（Domain Specified Language）名字
        // 实现动态传参
        val extension = project.extensions.create("myScriptExtension", MyScriptExtension::class.java)

        // 定义一个任务，可以使用gradle buildScriptPlugin执行
        project.tasks.create("buildMyPlugin") {
            it.group = "abelhu"
            it.doLast { println(extension.name) }
        }
    }
}

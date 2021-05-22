package com.abelhu.plugin

import com.abelhu.transform.CustomTransform
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.jvm.isAccessible

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

        // 注册一个transform
        val plugin = project.plugins.getPlugin(AppPlugin::class.java)
//        plugin.baseExtension.registerTransform(CustomTransform())
    }

//    private val AppPlugin.baseExtension: BaseExtension
//        get() {
//            return if (com.android.builder.model.Version.ANDROID_GRADLE_PLUGIN_VERSION == "3.0.0") {
//                val method = BasePlugin::class.declaredFunctions.first { it.name == "getExtension" }
//                method.isAccessible = true
//                method.call(this) as BaseExtension
//            } else {
//                extension
//            }
//        }
}

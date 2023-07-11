package com.abelhu.androidstudy.application

import android.app.Application
import android.view.Choreographer
import com.abelhu.androidstudy.BuildConfig
import com.abelhu.androidstudy.matrix.MatrixConfig
import com.abelhu.androidstudy.matrix.MatrixListener
import com.abelhu.androidstudy.ui.main.MainActivity
import com.tencent.matrix.Matrix
import com.tencent.matrix.iocanary.IOCanaryPlugin
import com.tencent.matrix.iocanary.config.IOConfig
import com.tencent.matrix.resource.ResourcePlugin
import com.tencent.matrix.resource.config.ResourceConfig
import com.tencent.matrix.trace.TracePlugin
import com.tencent.matrix.trace.config.TraceConfig
import com.tencent.mrs.plugin.IDynamicConfig
import dagger.hilt.android.HiltAndroidApp

// 所有使用 Hilt 的应用都必须包含一个带有 @HiltAndroidApp 注释的 Application 类
@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Choreographer.getInstance().postFrameCallback( FPSFrameCallback())
        registerActivityLifecycleCallbacks(MyApplicationLife.instance)
        initMatrix()
    }

    private fun initMatrix() {
        Matrix.Builder(this).pluginListener(MatrixListener(this))
            .apply {
                plugin(initIoCanary(MatrixConfig.instance))
                plugin(initTrace(MatrixConfig.instance))
                plugin(initResource(MatrixConfig.instance))
                Matrix.init(build()).plugins.forEach { it.start() }
            }
    }

    private fun initIoCanary(dynamicConfig: IDynamicConfig) = IOCanaryPlugin(IOConfig.Builder().dynamicConfig(dynamicConfig).build())

    private fun initTrace(dynamicConfig: IDynamicConfig) = TracePlugin(
        TraceConfig.Builder()
            .dynamicConfig(dynamicConfig)
            .enableFPS(BuildConfig.DEBUG)
            .enableEvilMethodTrace(BuildConfig.DEBUG)
            .enableAnrTrace(BuildConfig.DEBUG)
            .enableStartup(BuildConfig.DEBUG)
            .splashActivities(MainActivity::class.java.name)
            .isDebug(BuildConfig.DEBUG)
            .isDevEnv(BuildConfig.DEBUG)
            .build()
    )

    private fun initResource(dynamicConfig: IDynamicConfig) = ResourcePlugin(
        ResourceConfig.Builder().dynamicConfig(dynamicConfig).setAutoDumpHprofMode(ResourceConfig.DumpMode.MANUAL_DUMP).build()
    )


}
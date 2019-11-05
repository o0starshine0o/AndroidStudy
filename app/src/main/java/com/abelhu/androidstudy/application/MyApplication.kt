package com.abelhu.androidstudy.application

import android.annotation.SuppressLint
import android.app.Application
import com.abelhu.androidstudy.instrumentation.MyInstrumentation
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        // init logger
        Logger.addLogAdapter(AndroidLogAdapter())
        // set custom instrumentation
        setCustomInstrumentation()
    }

    @SuppressLint("PrivateApi", "DiscouragedPrivateApi")
    private fun setCustomInstrumentation() {
        val clazz = Class.forName("android.app.ActivityThread")
        val method = clazz.getDeclaredMethod("currentActivityThread")
        val currentActivityThread = method.invoke(null)
        val mInstrumentation = currentActivityThread.javaClass.getDeclaredField("mInstrumentation")
        mInstrumentation.isAccessible = true
        mInstrumentation.set(currentActivityThread, MyInstrumentation())
    }
}
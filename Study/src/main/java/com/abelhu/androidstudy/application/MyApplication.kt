package com.abelhu.androidstudy.application

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import com.abelhu.androidstudy.instrumentation.MyInstrumentation
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MyApplication : Application() {
    companion object {
        val Tag = MyApplication::class.simpleName
    }

    @SuppressLint("CheckResult")
    override fun onCreate() {
        super.onCreate()
        // set custom instrumentation
        setCustomInstrumentation()
        // set custom looper
        setCustomLooper()

        Single.just(true).subscribeOn(Schedulers.single()).flatMap { asyncNoRelySdk() }.observeOn(AndroidSchedulers.mainThread()).subscribe { e -> asyncRelySdk(e) }
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

    private fun setCustomLooper() {
        mainLooper.setMessageLogging { Log.i(Tag, it) }
        Log.i(Tag, applicationContext.filesDir.absolutePath)
    }

    private fun asyncNoRelySdk(): SingleSource<Boolean> {
        Log.i(Tag, "asyncNoRelySdk in thread:${Thread.currentThread().name}")
        Logger.addLogAdapter(AndroidLogAdapter())
        return Single.just(true)
    }

    private fun asyncRelySdk(e: Boolean) {
        Log.i(Tag, "asyncRelySdk in thread:${Thread.currentThread().name}")
    }
}
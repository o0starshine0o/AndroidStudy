package com.abelhu.androidstudy.application

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.multidex.MultiDex
import com.abelhu.androidstudy.instrumentation.MyInstrumentation
import com.abelhu.androidstudy.utils.TinkerManager
import com.qicode.extension.TAG
import com.tencent.tinker.anno.DefaultLifeCycle
import com.tencent.tinker.entry.DefaultApplicationLike
import com.tencent.tinker.lib.tinker.Tinker
import com.tencent.tinker.loader.shareutil.ShareConstants
import com.tinkerpatch.sdk.TinkerPatch
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * because you can not use any other class in your application, we need to
 * move your implement of Application to {@link ApplicationLifeCycle}
 * As Application, all its direct reference class should be in the main dex.
 *
 * We use tinker-android-anno to make sure all your classes can be patched.
 *
 * application: if it is start with '.', we will add SampleApplicationLifeCycle's package name
 *
 * flags:
 * TINKER_ENABLE_ALL: support dex, lib and resource
 * TINKER_DEX_MASK: just support dex
 * TINKER_NATIVE_LIBRARY_MASK: just support lib
 * TINKER_RESOURCE_MASK: just support resource
 *
 * loaderClass: define the tinker loader class, we can just use the default TinkerLoader
 *
 * loadVerifyFlag: whether check files' md5 on the load time, default it is false.
 */

@DefaultLifeCycle(
    application = ".MyApplication",             //application name to generate
    flags = ShareConstants.TINKER_ENABLE_ALL    //tinkerFlags above
//    flags = ShareConstants.TINKER_DISABLE    // 如果上架GOOGLE PLAY，必须解除tinker
)
class MyApplicationLike(val app: Application, tinkerFlags: Int, verifyFlag: Boolean, startElapsedTime: Long, startMillisTime: Long, resultIntent: Intent) :
    DefaultApplicationLike(app, tinkerFlags, verifyFlag, startElapsedTime, startMillisTime, resultIntent) {
    @SuppressLint("CheckResult")
    override fun onCreate() {
        super.onCreate()
        // set custom instrumentation, for UI test , temporarily remove this instrumentation
        setCustomInstrumentation()
        // set custom looper
        setCustomLooper()
        // init other sdk
        Single.just(true).subscribeOn(Schedulers.single()).flatMap { asyncNoRelySdk() }.observeOn(AndroidSchedulers.mainThread())
            .subscribe { e -> asyncRelySdk(e) }
    }

    /**
     * install multiDex before install tinker
     * so we don't need to put the tinker lib classes in the main dex
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    override fun onBaseContextAttached(context: Context) {
        super.onBaseContextAttached(context)
        //you must install multiDex whatever tinker is installed!
        MultiDex.install(context)
        // init TinkerManager
        TinkerManager.appLike = this
        // should set before tinker is installed
        TinkerManager.initFastCrashProtect()
        TinkerManager.setUpgradeRetryEnable(true)
        //installTinker after load multiDex or you can put com.tencent.tinker.** to main dex
        TinkerManager.installTinker(this)
        // for safer, you must use @{link TinkerInstaller.install} first!
        Tinker.with(application)
    }

    /**
     * change instrumentation to listener activity life-cycle
     */
    @SuppressLint("PrivateApi", "DiscouragedPrivateApi")
    private fun setCustomInstrumentation() {
        val clazz = Class.forName("android.app.ActivityThread")
        val method = clazz.getDeclaredMethod("currentActivityThread")
        val currentActivityThread = method.invoke(null)
        val mInstrumentation = currentActivityThread.javaClass.getDeclaredField("mInstrumentation")
        mInstrumentation.isAccessible = true
        mInstrumentation.set(currentActivityThread, MyInstrumentation())
    }

    /**
     * listener what happened to main thread
     */
    private fun setCustomLooper() {
        application.mainLooper.setMessageLogging { Log.i(TAG(), it) }
        Log.i(TAG(), application.applicationContext.filesDir.absolutePath)
    }

    /**
     * init sdk which not rely to main thread
     */
    private fun asyncNoRelySdk(): SingleSource<Boolean> {
        Log.i(TAG(), "asyncNoRelySdk in thread:${Thread.currentThread().name}")
        // init tinker patch
        initTinkerPatch()
        return Single.just(true)
    }
    /**
     * init sdk which rely to main thread
     */
    private fun asyncRelySdk(e: Boolean) {
        Log.i(TAG(), "asyncRelySdk in thread[${Thread.currentThread().name}]:$e")
    }

    private fun initTinkerPatch(){
        // 初始化TinkerPatch SDK, 更多配置可参照API章节中的,初始化 SDK
        TinkerPatch.init(this)
            .reflectPatchLibrary()
            .setPatchRollbackOnScreenOff(true)
            .setPatchRestartOnSrceenOff(true)
            .setFetchPatchIntervalByHours(3);

        // 每隔3个小时（通过setFetchPatchIntervalByHours设置）去访问后台时候有更新,通过handler实现轮训的效果
        TinkerPatch.with().fetchPatchUpdateAndPollWithInterval();

    }
}
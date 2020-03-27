package com.abelhu.androidstudy.application

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.multidex.MultiDex
import com.abelhu.androidstudy.BuildConfig
import com.abelhu.androidstudy.instrumentation.MyInstrumentation
import com.abelhu.androidstudy.tinker.*
import com.qicode.extension.TAG
import com.tencent.tinker.anno.DefaultLifeCycle
import com.tencent.tinker.entry.DefaultApplicationLike
import com.tencent.tinker.lib.service.PatchResult
import com.tencent.tinker.lib.tinker.Tinker
import com.tencent.tinker.lib.util.TinkerServiceInternals
import com.tencent.tinker.lib.util.UpgradePatchRetry
import com.tencent.tinker.loader.shareutil.ShareConstants
import com.tinkerpatch.sdk.TinkerPatch
import com.tinkerpatch.sdk.server.callback.ConfigRequestCallback
import com.tinkerpatch.sdk.server.callback.RollbackCallBack
import com.tinkerpatch.sdk.tinker.callback.ResultCallBack
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

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
        // 我们需要确保至少对主进程跟patch进程初始化 TinkerPatch
        if (TinkerServiceInternals.isInMainProcess(app.baseContext) || TinkerServiceInternals.isInPatchProcess(app.baseContext)) {
            // 根据创建参数选择使用哪种tinker, 需要紧跟super.onCreate()，否则容易出错
            when (BuildConfig.TINKER_TYPE) {
                // 初始化Tinker
                1 -> initTinker()
                // 初始化TinkerPatch
                2 -> initTinkerPatch(app.baseContext)
            }
        }
        if (TinkerServiceInternals.isInMainProcess(app.baseContext)) {
            // set custom instrumentation, for UI test , temporarily remove this instrumentation
            if (BuildConfig.CustomInstrumentation) setCustomInstrumentation()
            // set custom looper
            if (BuildConfig.CustomLooper) setCustomLooper()
            // listener activity life cycle
            app.registerActivityLifecycleCallbacks(MyApplicationLife())
            // init other sdk
            Single.just(true).subscribeOn(Schedulers.single()).flatMap { asyncNoRelySdk(app.baseContext) }.observeOn(AndroidSchedulers.mainThread())
                .subscribe { context -> asyncRelySdk(context) }
        }
    }

    /**
     * install multiDex before install tinker
     * so we don't need to put the tinker lib classes in the main dex
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    override fun onBaseContextAttached(context: Context) {
        super.onBaseContextAttached(context)
        if (TinkerServiceInternals.isInMainProcess(app.baseContext)) {
            //you must install multiDex whatever tinker is installed!
            MultiDex.install(context)
        }
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
    private fun asyncNoRelySdk(context: Context): SingleSource<Context> {
        Log.i(TAG(), "asyncNoRelySdk in thread:${Thread.currentThread().name}")
        return Single.just(context)
    }

    /**
     * init sdk which rely to main thread
     */
    private fun asyncRelySdk(context: Context) {
        Log.i(TAG(), "asyncRelySdk in thread[${Thread.currentThread().name}]")
    }

    /**
     * TinkerManager做成了静态类
     * 1、TinkerLog有默认的静态实现，所以如果没有特殊要求，默认的log是直接开启的
     * 2、initFastCrashProtect主要保护10秒内的快速崩溃，包括：Xposed提前加载类，3次快速崩溃限制
     * 3、TinkerReporter报告所有的Tinker信息，是TinkerReportHelper的默认实现，可以考虑要不要在这里面上报服务器
     */
    private fun initTinker() {
        // init TinkerManager
        TinkerManager.appLike = this
        // should set before tinker is installed
        TinkerManager.initFastCrashProtect()
        // 在PatchServiceStart的时候，创建retry.patch文件
        // 在线程空闲的时候尝试使用retry.patch文件进行重试补丁
        TinkerManager.setUpgradeRetryEnable(true)
        //installTinker after load multiDex or you can put com.tencent.tinker.** to main dex
        TinkerManager.installTinker(this)
        // for safer, you must use @{link TinkerInstaller.install} first!
        Tinker.with(application)
    }

    /**
     * 初始化TinkerPatch SDK, 更多配置可参照API章节中的,初始化 SDK
     * http://www.tinkerpatch.com/Docs/api
     *
     * 初始化的代码建议紧跟 super.onCreate(),并且所有进程都需要初始化，已达到所有进程都可以被 patch 的目的
     * 如果你确定只想在主进程中初始化 tinkerPatch，那也请至少在 :patch 进程中初始化，否则会有造成 :patch 进程crash，无法使补丁生效
     */
    private fun initTinkerPatch(context: Context) {
        // 允许重试
        UpgradePatchRetry.getInstance(context).setRetryEnable(true)
        // 所有tinker相关的模块都增加断点，方便查询哪里有问题
        val builder = TinkerPatch.Builder(this)
            .loadReporter(TinkerLoadReporter(context))
            .patchReporter(TinkerPatchReporter(context))
            .listener(TinkerPatchListener(context))
            .patchRequestCallback(TinkerRequestCallback())
            .upgradePatch(TinkerUpgradePatch())
            .resultServiceClass(TinkerResultService::class.java)
            .build()

        TinkerPatch.init(builder)
            // /是否自动反射Library路径,无须手动加载补丁中的So文件 注意,调用在反射接口之后才能生效,你也可以使用Tinker的方式加载Library
            .reflectPatchLibrary()
            // 向后台获取是否有补丁包更新,默认的访问间隔为3个小时，若参数为true,即每次调用都会真正的访问后台配置
            // 你也可以在用户登录或者APP启动等一些关键路径，使用fetchPatchUpdate(true)强制检查更新
            .fetchPatchUpdate(false)
            // 设置访问后台补丁包更新配置的时间间隔,默认为3个小时
            .setFetchPatchIntervalByHours(3)
            //设置访问后台动态配置的时间间隔,默认为3个小时
            .setFetchDynamicConfigIntervalByHours(3)
            //若参数为true,即每次调用都会真正的访问后台配置
            .fetchDynamicConfig(object : ConfigRequestCallback {
                override fun onSuccess(map: HashMap<String, String>) {
                    Log.i(TAG(), "fetchDynamicConfig onSuccess")
                    for ((key, value) in map) {
                        Log.i(TAG(), "$key : $value")
                    }
                }

                override fun onFail(e: Exception?) {
                    Log.i(TAG(), "fetchDynamicConfig onFail: $e")
                }
            }, false)
            //设置当前渠道号,对于某些渠道我们可能会想屏蔽补丁功能；设置渠道后,我们就可以使用后台的条件控制渠道更新
            .setAppChannel("default")
            //屏蔽部分渠道的补丁功能
            .addIgnoreAppChannel("google-play")
            //设置tinker-patch平台的条件下发参数
            .setPatchCondition("user", "1")
            //设置补丁合成成功后,锁屏重启程序,默认是等应用自然重启
            .setPatchRestartOnSrceenOff(true)
            //我们可以通过ResultCallBack设置对合成后的回调,例如弹框什么
            //注意，setPatchResultCallback 的回调是运行在 intentService 的线程中
            // tinker 不能使用lambda
            .setPatchResultCallback(object : ResultCallBack {
                override fun onPatchResult(result: PatchResult?) {
                    Log.i(TAG(), "onPatchResultCallback: $result")
                }
            })
            //设置收到后台回退要求时,锁屏清除补丁,默认是等主进程重启时自动清除
            .setPatchRollbackOnScreenOff(true)
            //我们可以通过RollbackCallBack设置对回退时的回调
            // tinker 不能使用lambda
            .setPatchRollBackCallback(object : RollbackCallBack {
                override fun onPatchRollback() {
                    Log.i(TAG(), "onPatchRollBackCallback")
                }
            })

        // 每隔3个小时（通过setFetchPatchIntervalByHours设置）去访问后台时候有更新,通过handler实现轮训的效果
        TinkerPatch.with().fetchPatchUpdateAndPollWithInterval()
        // 在TinkerPatch sdk 1.1.4 增加的接口，获取当前补丁版本号
        Log.i(TAG(), "tinker patch version: ${TinkerPatch.with().patchVersion}")
    }
}
package com.abelhu.androidstudy.tinker

import com.qicode.extension.TAG
import com.tencent.tinker.entry.ApplicationLike
import com.tencent.tinker.lib.patch.UpgradePatch
import com.tencent.tinker.lib.tinker.TinkerInstaller
import com.tencent.tinker.lib.util.TinkerLog
import com.tencent.tinker.lib.util.UpgradePatchRetry

object TinkerManager {
    /**
     * the error code define by myself
     * should after `ShareConstants.ERROR_PATCH_IN_SERVICE`
     */
    const val ERROR_PATCH_GOOGLE_PLAY_CHANNEL = -20
    const val ERROR_PATCH_ROM_SPACE = -21
    const val ERROR_PATCH_MEMORY_LIMIT = -22
    const val ERROR_PATCH_CRASH_LIMIT = -23
    const val ERROR_PATCH_CONDITION_NOT_SATISFIED = -24

    const val PLATFORM = "platform"
    const val MIN_MEMORY_HEAP_SIZE = 45

    var appLike: ApplicationLike? = null
    private var uncaughtExceptionHandler: TinkerExceptionHandler? = null
    private var isInstalled = false

    fun initFastCrashProtect() {
        if (uncaughtExceptionHandler == null) {
            // 因为TinkerExceptionHandler保存了默认的ExceptionHandler，所以这里其实是插入了一个TinkerExceptionHandler，而非直接替换
            uncaughtExceptionHandler = TinkerExceptionHandler()
            Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler)
        }
    }

    fun setUpgradeRetryEnable(enable: Boolean) = appLike?.also { UpgradePatchRetry.getInstance(it.application).setRetryEnable(enable) }

    /**
     * all use default class, simply Tinker install method
     */
    fun sampleInstallTinker(appLike: ApplicationLike?) {
        if (isInstalled) TinkerLog.w(TAG(), "install tinker, but has installed, ignore")
        else {
            TinkerInstaller.install(appLike)
            isInstalled = true
        }
    }

    /**
     * you can specify all class you want.
     * sometimes, you can only install tinker in some process you want!
     */
    fun installTinker(appLike: ApplicationLike) {
        if (isInstalled) return TinkerLog.w(TAG(), "install tinker, but has installed, ignore")
        //or you can just use DefaultLoadReporter， 主要报告patch包的加载过程
        val loadReporter = TinkerLoadReporter(appLike.application)
        //or you can just use DefaultPatchReporter
        val patchReporter = TinkerPatchReporter(appLike.application)
        //or you can just use DefaultPatchListener， 主要是检测是否需要调起：patch进程进行合并操作， 设置自己的条件过滤（rom大小，崩溃次数，渠道配置等）
        val patchListener = TinkerPatchListener(appLike.application)
        // 打补丁的结果, tinker默认提供的是清理补丁文件，直接kill进程
        // 这里优化为app进入后台或者锁屏时重新启动app
        val resultService = TinkerResultService::class.java
        //you can set your own upgrade patch if you need
        // 检测是否启用tinker， 补丁是否合法，tinkerID检测，设置result的md5， 新旧版本判断， 复制patch（原始文件可能被删除），适配方舟编译器， 恢复一些文件
        val upgradePatchProcessor = UpgradePatch()
        // install tinker
        TinkerInstaller.install(appLike, loadReporter, patchReporter, patchListener, resultService, upgradePatchProcessor)
        // set flag
        isInstalled = true
    }
}
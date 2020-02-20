package com.abelhu.androidstudy.utils

import com.abelhu.androidstudy.crash.UncaughtExceptionHandler
import com.abelhu.androidstudy.reporter.ThinkPatchReporter
import com.abelhu.androidstudy.reporter.TinkerLoadReporter
import com.abelhu.androidstudy.reporter.TinkerPatchListener
import com.abelhu.androidstudy.service.TinkerResultService
import com.qicode.extension.TAG
import com.tencent.tinker.entry.ApplicationLike
import com.tencent.tinker.lib.patch.AbstractPatch
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
    private var uncaughtExceptionHandler: UncaughtExceptionHandler? = null
    private var isInstalled = false

    fun initFastCrashProtect() {
        if (uncaughtExceptionHandler == null) {
            uncaughtExceptionHandler = UncaughtExceptionHandler()
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
     *
     * @param appLike
     */
    fun installTinker(appLike: ApplicationLike) {
        if (isInstalled) TinkerLog.w(TAG(), "install tinker, but has installed, ignore")
        else {
            //or you can just use DefaultLoadReporter
            val loadReporter = TinkerLoadReporter(appLike.application)
            //or you can just use DefaultPatchReporter
            val patchReporter = ThinkPatchReporter(appLike.application)
            //or you can just use DefaultPatchListener
            val patchListener = TinkerPatchListener(appLike.application)
            //you can set your own upgrade patch if you need
            val upgradePatchProcessor: AbstractPatch = UpgradePatch()
            TinkerInstaller.install(appLike, loadReporter, patchReporter, patchListener, TinkerResultService::class.java, upgradePatchProcessor)
            isInstalled = true
        }
    }
}
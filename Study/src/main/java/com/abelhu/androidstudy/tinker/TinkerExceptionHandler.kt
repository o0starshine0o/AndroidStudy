//package com.abelhu.androidstudy.tinker
//
//import android.content.Context
//import android.os.SystemClock
//import com.qicode.extension.TAG
//import com.tencent.tinker.lib.tinker.TinkerApplicationHelper
//import com.tencent.tinker.lib.util.TinkerLog
//import com.tencent.tinker.loader.shareutil.ShareConstants
//import com.tencent.tinker.loader.shareutil.ShareTinkerInternals
//
//
///**
// * optional, use dynamic configuration is better way for native crash
// *
// * tinker's crash is caught by `LoadReporter.onLoadException`
// * use `TinkerApplicationHelper` api, no need to install tinker!
// */
//class TinkerExceptionHandler : Thread.UncaughtExceptionHandler {
//    companion object {
//        private const val QUICK_CRASH_ELAPSE = 10 * 1000.toLong()
//        const val MAX_CRASH_COUNT = 3
//        private const val DALVIK_XPOSED_CRASH = "Class ref in pre-verified class resolved to unexpected implementation"
//    }
//
//    // 保留系统默认的异常处理
//    private val handler = Thread.getDefaultUncaughtExceptionHandler()
//
//    override fun uncaughtException(thread: Thread, ex: Throwable) {
//        TinkerLog.e(TAG(), "uncaughtException:" + ex.message)
//        tinkerFastCrashProtect()
//        tinkerPreVerifiedCrashHandler(ex)
//        handler?.uncaughtException(thread, ex)
//    }
//
//    /**
//     * Such as Xposed, if it try to load some class before we load from patch files.
//     * With dalvik, it will crash with "Class ref in pre-verified class resolved to unexpected implementation".
//     * With art, it may crash at some times. But we can't know the actual crash type.
//     * If it use Xposed, we can just clean patch or mention user to uninstall it.
//     */
//    private fun tinkerPreVerifiedCrashHandler(e: Throwable?) {
//        val applicationLike = TinkerManager.appLike
//        if (applicationLike == null || applicationLike.application == null) return
//        if (!TinkerApplicationHelper.isTinkerLoadSuccess(applicationLike)) return
//
//        var isXposed = false
//        var throwable = e
//        while (throwable != null) {
//            if (!isXposed) isXposed = TinkerUtils.isXposedExists(throwable)
//            // xposed?
//            if (isXposed) {
//                //for art, we can't know the actually crash type just ignore art
//                //for dalvik, we know the actual crash type
//                val isCausedByXposed = throwable is IllegalAccessError && throwable.message!!.contains(DALVIK_XPOSED_CRASH)
//                if (isCausedByXposed) {
//                    val message = "have xposed: just clean tinker"
//                    TinkerReportHelper.onXposedCrash(message)
//                    TinkerLog.e(TAG(), message)
//                    //kill all other process to ensure that all process's code is the same.
//                    ShareTinkerInternals.killAllOtherProcess(applicationLike.application)
//                    TinkerApplicationHelper.cleanPatch(applicationLike)
//                    ShareTinkerInternals.setTinkerDisableWithSharedPreferences(applicationLike.application)
//                    return
//                }
//            }
//            throwable = throwable.cause
//        }
//    }
//
//    /**
//     * if tinker is load, and it crash more than MAX_CRASH_COUNT, then we just clean patch.
//     */
//    @Suppress("DEPRECATION")
//    private fun tinkerFastCrashProtect(): Boolean {
//        val appLike = TinkerManager.appLike
//        if (appLike == null || appLike.application == null) return false
//        if (!TinkerApplicationHelper.isTinkerLoadSuccess(appLike)) return false
//
//        val elapsedTime = SystemClock.elapsedRealtime() - appLike.applicationStartElapsedTime
//        //this process may not install tinker, so we use TinkerApplicationHelper api
//        if (elapsedTime < QUICK_CRASH_ELAPSE) {
//            val currentVersion = TinkerApplicationHelper.getCurrentVersion(appLike)
//            if (ShareTinkerInternals.isNullOrNil(currentVersion)) return false
//            val sp = appLike.application.getSharedPreferences(ShareConstants.TINKER_SHARE_PREFERENCE_CONFIG, Context.MODE_MULTI_PROCESS)
//            val fastCrashCount = sp.getInt(currentVersion, 0) + 1
//            if (fastCrashCount >= MAX_CRASH_COUNT) {
//                TinkerApplicationHelper.cleanPatch(appLike)
//                val message = "tinker has fast crash more than $fastCrashCount, we just clean patch!"
//                TinkerReportHelper.onFastCrashProtect(message)
//                TinkerLog.e(TAG(), message)
//                return true
//            } else {
//                sp.edit().putInt(currentVersion, fastCrashCount).apply()
//                TinkerLog.e(TAG(), "tinker has fast crash %d times", fastCrashCount)
//            }
//        }
//        return false
//    }
//}

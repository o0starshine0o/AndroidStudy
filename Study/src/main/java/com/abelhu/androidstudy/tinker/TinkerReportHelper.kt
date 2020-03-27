package com.abelhu.androidstudy.tinker

import com.abelhu.androidstudy.tinker.TinkerManager.ERROR_PATCH_CONDITION_NOT_SATISFIED
import com.abelhu.androidstudy.tinker.TinkerManager.ERROR_PATCH_CRASH_LIMIT
import com.abelhu.androidstudy.tinker.TinkerManager.ERROR_PATCH_GOOGLE_PLAY_CHANNEL
import com.abelhu.androidstudy.tinker.TinkerManager.ERROR_PATCH_MEMORY_LIMIT
import com.abelhu.androidstudy.tinker.TinkerManager.ERROR_PATCH_ROM_SPACE
import com.qicode.extension.TAG
import com.tencent.tinker.lib.util.TinkerLog
import com.tencent.tinker.loader.shareutil.ShareConstants
import com.tencent.tinker.loader.shareutil.ShareConstants.*
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals
import java.io.File


/**
 * a simple tinker data tinker
 */
object TinkerReportHelper {
    interface Reporter {
        fun onReport(key: Int, message: String? = null)
        fun onReport(message: String?)
    }

    // KEY - PV
    private const val KEY_REQUEST = 0
    private const val KEY_DOWNLOAD = 1
    private const val KEY_TRY_APPLY = 2
    private const val KEY_TRY_APPLY_SUCCESS = 3
    private const val KEY_APPLIED_START = 4
    private const val KEY_APPLIED = 5
    private const val KEY_LOADED = 6
    private const val KEY_CRASH_FAST_PROTECT = 7
    private const val KEY_CRASH_CAUSE_XPOSED_DALVIK = 8
    private const val KEY_CRASH_CAUSE_XPOSED_ART = 9
    private const val KEY_APPLY_WITH_RETRY = 10
    //Key -- try apply detail
    private const val KEY_TRY_APPLY_UPGRADE = 70
    private const val KEY_TRY_APPLY_DISABLE = 71
    private const val KEY_TRY_APPLY_RUNNING = 72
    private const val KEY_TRY_APPLY_IN_SERVICE = 73
    private const val KEY_TRY_APPLY_NOT_EXIST = 74
    private const val KEY_TRY_APPLY_GOOGLE_PLAY = 75
    private const val KEY_TRY_APPLY_ROM_SPACE = 76
    private const val KEY_TRY_APPLY_ALREADY_APPLY = 77
    private const val KEY_TRY_APPLY_MEMORY_LIMIT = 78
    private const val KEY_TRY_APPLY_CRASH_LIMIT = 79
    private const val KEY_TRY_APPLY_CONDITION_NOT_SATISFIED = 80
    private const val KEY_TRY_APPLY_JIT = 81
    //Key -- apply detail
    private const val KEY_APPLIED_UPGRADE = 100
    private const val KEY_APPLIED_UPGRADE_FAIL = 101
    private const val KEY_APPLIED_EXCEPTION = 120
    private const val KEY_APPLIED_DEX_OPT_OTHER = 121
    private const val KEY_APPLIED_DEX_OPT_EXIST = 122
    private const val KEY_APPLIED_DEX_OPT_FORMAT = 123
    private const val KEY_APPLIED_INFO_CORRUPTED = 124
    //package check
    private const val KEY_APPLIED_PACKAGE_CHECK_SIGNATURE = 150
    private const val KEY_APPLIED_PACKAGE_CHECK_DEX_META = 151
    private const val KEY_APPLIED_PACKAGE_CHECK_LIB_META = 152
    private const val KEY_APPLIED_PACKAGE_CHECK_APK_TINKER_ID_NOT_FOUND = 153
    private const val KEY_APPLIED_PACKAGE_CHECK_PATCH_TINKER_ID_NOT_FOUND = 154
    private const val KEY_APPLIED_PACKAGE_CHECK_META_NOT_FOUND = 155
    private const val KEY_APPLIED_PACKAGE_CHECK_TINKER_ID_NOT_EQUAL = 156
    private const val KEY_APPLIED_PACKAGE_CHECK_RES_META = 157
    private const val KEY_APPLIED_PACKAGE_CHECK_TINKER_FLAG_NOT_SUPPORT = 158
    //version check
    private const val KEY_APPLIED_VERSION_CHECK = 180
    //extract error
    private const val KEY_APPLIED_PATCH_FILE_EXTRACT = 181
    private const val KEY_APPLIED_DEX_EXTRACT = 182
    private const val KEY_APPLIED_LIB_EXTRACT = 183
    private const val KEY_APPLIED_RESOURCE_EXTRACT = 184
    //cost time
    private const val KEY_APPLIED_SUCC_COST_5S_LESS = 200
    private const val KEY_APPLIED_SUCC_COST_10S_LESS = 201
    private const val KEY_APPLIED_SUCC_COST_30S_LESS = 202
    private const val KEY_APPLIED_SUCC_COST_60S_LESS = 203
    private const val KEY_APPLIED_SUCC_COST_OTHER = 204
    private const val KEY_APPLIED_FAIL_COST_5S_LESS = 205
    private const val KEY_APPLIED_FAIL_COST_10S_LESS = 206
    private const val KEY_APPLIED_FAIL_COST_30S_LESS = 207
    private const val KEY_APPLIED_FAIL_COST_60S_LESS = 208
    private const val KEY_APPLIED_FAIL_COST_OTHER = 209
    // KEY -- load detail
    private const val KEY_LOADED_UNKNOWN_EXCEPTION = 250
    private const val KEY_LOADED_UNCAUGHT_EXCEPTION = 251
    private const val KEY_LOADED_EXCEPTION_DEX = 252
    private const val KEY_LOADED_EXCEPTION_DEX_CHECK = 253
    private const val KEY_LOADED_EXCEPTION_RESOURCE = 254
    private const val KEY_LOADED_EXCEPTION_RESOURCE_CHECK = 255
    private const val KEY_LOADED_MISMATCH_DEX = 300
    private const val KEY_LOADED_MISMATCH_LIB = 301
    private const val KEY_LOADED_MISMATCH_RESOURCE = 302
    private const val KEY_LOADED_MISSING_DEX = 303
    private const val KEY_LOADED_MISSING_LIB = 304
    private const val KEY_LOADED_MISSING_PATCH_FILE = 305
    private const val KEY_LOADED_MISSING_PATCH_INFO = 306
    private const val KEY_LOADED_MISSING_DEX_OPT = 307
    private const val KEY_LOADED_MISSING_RES = 308
    private const val KEY_LOADED_INFO_CORRUPTED = 309
    //load package check
    private const val KEY_LOADED_PACKAGE_CHECK_SIGNATURE = 350
    private const val KEY_LOADED_PACKAGE_CHECK_DEX_META = 351
    private const val KEY_LOADED_PACKAGE_CHECK_LIB_META = 352
    private const val KEY_LOADED_PACKAGE_CHECK_APK_TINKER_ID_NOT_FOUND = 353
    private const val KEY_LOADED_PACKAGE_CHECK_PATCH_TINKER_ID_NOT_FOUND = 354
    private const val KEY_LOADED_PACKAGE_CHECK_TINKER_ID_NOT_EQUAL = 355
    private const val KEY_LOADED_PACKAGE_CHECK_PACKAGE_META_NOT_FOUND = 356
    private const val KEY_LOADED_PACKAGE_CHECK_RES_META = 357
    private const val KEY_LOADED_PACKAGE_CHECK_TINKER_FLAG_NOT_SUPPORT = 358
    private const val KEY_LOADED_SUCCESS_COST_500_LESS = 400
    private const val KEY_LOADED_SUCCESS_COST_1000_LESS = 401
    private const val KEY_LOADED_SUCCESS_COST_3000_LESS = 402
    private const val KEY_LOADED_SUCCESS_COST_5000_LESS = 403
    private const val KEY_LOADED_SUCCESS_COST_OTHER = 404
    private const val KEY_LOADED_INTERPRET_GET_INSTRUCTION_SET_ERROR = 450
    private const val KEY_LOADED_INTERPRET_INTERPRET_COMMAND_ERROR = 451
    private const val KEY_LOADED_INTERPRET_TYPE_INTERPRET_OK = 452

    var reporter: Reporter? = TinkerReporter()

    fun onTryApply(success: Boolean) {
        reporter?.onReport(KEY_TRY_APPLY)
        reporter?.onReport(KEY_TRY_APPLY_UPGRADE)
        if (success) reporter?.onReport(KEY_TRY_APPLY_SUCCESS)
    }

    fun onLoadPatchListenerReceiveFail(patchFile: File, errorCode: Int) {
        val message = "patch loadReporter onLoadPatchListenerReceiveFail: patch receive fail: ${patchFile.absolutePath}, code: "
        when (errorCode) {
            // 输入的临时补丁包文件不存在。
            ERROR_PATCH_NOTEXIST -> reporter?.onReport(KEY_TRY_APPLY_NOT_EXIST, "$message KEY_TRY_APPLY_NOT_EXIST")
            // 当前tinkerFlag为不可用状态。
            ERROR_PATCH_DISABLE -> reporter?.onReport(KEY_TRY_APPLY_DISABLE, "$message KEY_TRY_APPLY_DISABLE")
            // 不能在:patch补丁合成进程，发起补丁的合成请求。
            ERROR_PATCH_INSERVICE -> reporter?.onReport(KEY_TRY_APPLY_IN_SERVICE, "$message KEY_TRY_APPLY_IN_SERVICE")
            // 当前:patch补丁合成进程正在运行。
            ERROR_PATCH_RUNNING -> reporter?.onReport(KEY_TRY_APPLY_RUNNING, "$message KEY_TRY_APPLY_RUNNING")
            // 补丁不支持 N 之前的 JIT 模式。
            ERROR_PATCH_JIT -> reporter?.onReport(KEY_TRY_APPLY_JIT, "$message KEY_TRY_APPLY_JIT")
            // 补丁已经应用。
            ERROR_PATCH_ALREADY_APPLY -> reporter?.onReport(KEY_TRY_APPLY_ALREADY_APPLY, "$message KEY_TRY_APPLY_ALREADY_APPLY")
            // ROM空间不够
            ERROR_PATCH_ROM_SPACE -> reporter?.onReport(KEY_TRY_APPLY_ROM_SPACE, "$message KEY_TRY_APPLY_ROM_SPACE")
            // GP渠道
            ERROR_PATCH_GOOGLE_PLAY_CHANNEL -> reporter?.onReport(KEY_TRY_APPLY_GOOGLE_PLAY, "$message KEY_TRY_APPLY_GOOGLE_PLAY")
            // 崩溃限制
            ERROR_PATCH_CRASH_LIMIT -> reporter?.onReport(KEY_TRY_APPLY_CRASH_LIMIT, "$message KEY_TRY_APPLY_CRASH_LIMIT")
            // 内存限制
            ERROR_PATCH_MEMORY_LIMIT -> reporter?.onReport(KEY_TRY_APPLY_MEMORY_LIMIT, "$message KEY_TRY_APPLY_MEMORY_LIMIT")
            // 天剑不满足
            ERROR_PATCH_CONDITION_NOT_SATISFIED -> reporter?.onReport(KEY_TRY_APPLY_CONDITION_NOT_SATISFIED, "$message KEY_TRY_APPLY_CONDITION_NOT_SATISFIED")
        }
    }

    fun onLoadPackageCheckFail(patchFile: File, errorCode: Int) {
        val message = "patch loadReporter onLoadPackageCheckFail: load patch package check fail file path: ${patchFile.absolutePath}, errorCode: "
        when (errorCode) {
            // 签名校验失败
            ERROR_PACKAGE_CHECK_SIGNATURE_FAIL -> reporter?.onReport(KEY_LOADED_PACKAGE_CHECK_SIGNATURE, "$message KEY_LOADED_PACKAGE_CHECK_SIGNATURE")
            ERROR_PACKAGE_CHECK_DEX_META_CORRUPTED -> reporter?.onReport(KEY_LOADED_PACKAGE_CHECK_DEX_META, "$message KEY_LOADED_PACKAGE_CHECK_DEX_META")
            ERROR_PACKAGE_CHECK_LIB_META_CORRUPTED -> reporter?.onReport(KEY_LOADED_PACKAGE_CHECK_LIB_META, "$message KEY_LOADED_PACKAGE_CHECK_LIB_META")
            ERROR_PACKAGE_CHECK_PATCH_TINKER_ID_NOT_FOUND -> reporter?.onReport(
                KEY_LOADED_PACKAGE_CHECK_PATCH_TINKER_ID_NOT_FOUND,
                "$message KEY_LOADED_PACKAGE_CHECK_PATCH_TINKER_ID_NOT_FOUND"
            )
            // 找不到基准apk AndroidManifest中的TINKER_ID
            ERROR_PACKAGE_CHECK_APK_TINKER_ID_NOT_FOUND -> reporter?.onReport(
                KEY_LOADED_PACKAGE_CHECK_APK_TINKER_ID_NOT_FOUND,
                "$message KEY_LOADED_PACKAGE_CHECK_APK_TINKER_ID_NOT_FOUND"
            )
            // 基准版本与补丁定义的TINKER_ID不相等
            ERROR_PACKAGE_CHECK_TINKER_ID_NOT_EQUAL -> reporter?.onReport(
                KEY_LOADED_PACKAGE_CHECK_TINKER_ID_NOT_EQUAL,
                "$message KEY_LOADED_PACKAGE_CHECK_TINKER_ID_NOT_EQUAL"
            )
            // 找不到"assets/package_meta.txt"文件
            ERROR_PACKAGE_CHECK_PACKAGE_META_NOT_FOUND -> reporter?.onReport(
                KEY_LOADED_PACKAGE_CHECK_PACKAGE_META_NOT_FOUND,
                "$message KEY_LOADED_PACKAGE_CHECK_PACKAGE_META_NOT_FOUND"
            )
            // "assets/res_meta.txt"信息损坏
            ERROR_PACKAGE_CHECK_RESOURCE_META_CORRUPTED -> reporter?.onReport(KEY_LOADED_PACKAGE_CHECK_RES_META, "$message KEY_LOADED_PACKAGE_CHECK_RES_META")
            // tinkerFlag不支持补丁中的某些类型的更改，例如补丁中存在资源更新，但是使用者指定不支持资源类型更新。
            ERROR_PACKAGE_CHECK_TINKERFLAG_NOT_SUPPORT -> reporter?.onReport(
                KEY_LOADED_PACKAGE_CHECK_TINKER_FLAG_NOT_SUPPORT,
                "$message KEY_LOADED_PACKAGE_CHECK_TINKER_FLAG_NOT_SUPPORT"
            )
        }
    }

    fun onLoaded(cost: Long) {
        val message = "tinker onLoaded with cost: $cost"
        reporter?.onReport(KEY_LOADED, message)
        if (cost < 0L) {
            TinkerLog.e(TAG(), "hp_report report load cost failed, invalid cost")
            return
        }
        when {
            cost <= 500 -> reporter?.onReport(KEY_LOADED_SUCCESS_COST_500_LESS, message)
            cost <= 1000 -> reporter?.onReport(KEY_LOADED_SUCCESS_COST_1000_LESS, message)
            cost <= 3000 -> reporter?.onReport(KEY_LOADED_SUCCESS_COST_3000_LESS, message)
            cost <= 5000 -> reporter?.onReport(KEY_LOADED_SUCCESS_COST_5000_LESS, message)
            else -> reporter?.onReport(KEY_LOADED_SUCCESS_COST_OTHER, message)
        }
    }

    fun onLoadInfoCorrupted(old: String, new: String, file: File) {
        val message = "patch loadReporter onLoadPatchInfoCorrupted: patch info file damage: ${file.absolutePath}, from version: $old to version: $new"
        reporter?.onReport(KEY_LOADED_INFO_CORRUPTED, message)
    }

    fun onLoadFileNotFound(file: File, fileType: Int, isDirectory: Boolean) {
        val message = "patch loadReporter onLoadFileNotFound: patch file not found: ${file.absolutePath}, isDirectory: $isDirectory, fileType: "
        when (fileType) {
            // odex文件
            TYPE_DEX_OPT -> reporter?.onReport(KEY_LOADED_MISSING_DEX_OPT, "$message KEY_LOADED_MISSING_DEX_OPT")
            TYPE_DEX -> reporter?.onReport(KEY_LOADED_MISSING_DEX, "$message KEY_LOADED_MISSING_DEX")
            // library文件
            TYPE_LIBRARY -> reporter?.onReport(KEY_LOADED_MISSING_LIB, "$message KEY_LOADED_MISSING_LIB")
            TYPE_PATCH_FILE -> reporter?.onReport(KEY_LOADED_MISSING_PATCH_FILE, "$message KEY_LOADED_MISSING_PATCH_FILE")
            // "patch.info"补丁版本配置文件
            TYPE_PATCH_INFO -> reporter?.onReport(KEY_LOADED_MISSING_PATCH_INFO, "$message KEY_LOADED_MISSING_PATCH_INFO")
            // 资源文件
            TYPE_RESOURCE -> reporter?.onReport(KEY_LOADED_MISSING_RES, "$message KEY_LOADED_MISSING_RES")
        }
    }

    fun onLoadInterpretReport(type: Int, e: Throwable?) {
        when (type) {
            TYPE_INTERPRET_GET_INSTRUCTION_SET_ERROR -> {
                reporter?.onReport(
                    KEY_LOADED_INTERPRET_GET_INSTRUCTION_SET_ERROR,
                    "patch loadReporter onLoadInterpret fail, can get instruction set from existed oat file"
                )
            }
            TYPE_INTERPRET_COMMAND_ERROR -> {
                reporter?.onReport(
                    KEY_LOADED_INTERPRET_INTERPRET_COMMAND_ERROR,
                    "patch loadReporter onLoadInterpret fail, command line to interpret return error"
                )
            }
            TYPE_INTERPRET_OK -> reporter?.onReport(KEY_LOADED_INTERPRET_TYPE_INTERPRET_OK, "patch loadReporter onLoadInterpret ok")
        }
    }

    fun onLoadFileMisMatch(file: File, fileType: Int) {
        val message = "patch load Reporter onLoadFileMd5Mismatch: patch file md5 mismatch file: ${file.absolutePath}, fileType: "
        when (fileType) {
            // 在Dalvik合成全量的Dex文件
            TYPE_DEX -> reporter?.onReport(KEY_LOADED_MISMATCH_DEX, "$message KEY_LOADED_MISMATCH_DEX")
            TYPE_LIBRARY -> reporter?.onReport(KEY_LOADED_MISMATCH_LIB, "$message KEY_LOADED_MISMATCH_LIB")
            TYPE_RESOURCE -> reporter?.onReport(KEY_LOADED_MISMATCH_RESOURCE, "$message KEY_LOADED_MISMATCH_RESOURCE")
        }
    }

    fun onLoadException(throwable: Throwable, errorCode: Int) {
        var isCheckFail = false
        when (errorCode) {
            // 在加载dex过程中捕获到的crash
            ShareConstants.ERROR_LOAD_EXCEPTION_DEX -> if (throwable.message!!.contains(ShareConstants.CHECK_DEX_INSTALL_FAIL)) {
                isCheckFail = true
                val message = "tinker dex check fail: ${throwable.message}"
                reporter?.onReport(KEY_LOADED_EXCEPTION_DEX_CHECK, message)
                TinkerLog.e(TAG(), message)
            } else {
                val message = "tinker dex reflect fail: ${throwable.message}"
                reporter?.onReport(KEY_LOADED_EXCEPTION_DEX, message)
                TinkerLog.e(TAG(), message)
            }
            // 在加载res过程中捕获到的crash
            ShareConstants.ERROR_LOAD_EXCEPTION_RESOURCE -> if (throwable.message!!.contains(ShareConstants.CHECK_RES_INSTALL_FAIL)) {
                isCheckFail = true
                val message = "tinker res check fail: ${throwable.message}"
                reporter?.onReport(KEY_LOADED_EXCEPTION_RESOURCE_CHECK, message)
                TinkerLog.e(TAG(), message)
            } else {
                val message = "tinker res reflect fail: ${throwable.message}"
                reporter?.onReport(KEY_LOADED_EXCEPTION_RESOURCE, message)
                TinkerLog.e(TAG(), message)
            }
            // 没有捕获到的非java crash,这个是补丁机制的安全模式
            ShareConstants.ERROR_LOAD_EXCEPTION_UNCAUGHT -> reporter?.onReport(KEY_LOADED_UNCAUGHT_EXCEPTION, "KEY_LOADED_UNCAUGHT_EXCEPTION")
            // 没有捕获到的java crash
            ShareConstants.ERROR_LOAD_EXCEPTION_UNKNOWN -> reporter?.onReport(KEY_LOADED_UNKNOWN_EXCEPTION, "KEY_LOADED_UNKNOWN_EXCEPTION")
        }
        //tinker exception, for dex check fail, we don't need to report stacktrace
        if (!isCheckFail) reporter?.onReport("Tinker Exception:load tinker occur exception ")
    }

    fun onApplyPatchServiceStart() = reporter?.onReport(KEY_APPLIED_START)

    fun onApplyDexOptFail(throwable: Throwable) {
        when {
            throwable.message!!.contains(ShareConstants.CHECK_DEX_OAT_EXIST_FAIL) -> reporter?.onReport(KEY_APPLIED_DEX_OPT_EXIST)
            throwable.message!!.contains(ShareConstants.CHECK_DEX_OAT_FORMAT_FAIL) -> reporter?.onReport(KEY_APPLIED_DEX_OPT_FORMAT)
            else -> reporter?.onReport(KEY_APPLIED_DEX_OPT_OTHER)
        }
    }

    fun onApplyInfoCorrupted() = reporter?.onReport(KEY_APPLIED_INFO_CORRUPTED)

    fun onApplyVersionCheckFail() = reporter?.onReport(KEY_APPLIED_VERSION_CHECK)

    fun onApplyExtractFail(fileType: Int) {
        when (fileType) {
            TYPE_DEX -> reporter?.onReport(KEY_APPLIED_DEX_EXTRACT)
            TYPE_LIBRARY -> reporter?.onReport(KEY_APPLIED_LIB_EXTRACT)
            // 补丁文件
            TYPE_PATCH_FILE -> reporter?.onReport(KEY_APPLIED_PATCH_FILE_EXTRACT)
            TYPE_RESOURCE -> reporter?.onReport(KEY_APPLIED_RESOURCE_EXTRACT)
        }
    }

    fun onApplied(cost: Long, success: Boolean) {
        if (success) {
            reporter?.onReport(KEY_APPLIED)
        }
        if (success) {
            reporter?.onReport(KEY_APPLIED_UPGRADE)
        } else {
            reporter?.onReport(KEY_APPLIED_UPGRADE_FAIL)
        }
        TinkerLog.i(TAG(), "hp_report report apply cost = %d", cost)
        if (cost < 0L) {
            TinkerLog.e(TAG(), "hp_report report apply cost failed, invalid cost")
            return
        }
        if (cost <= 5000) {
            if (success) {
                reporter?.onReport(KEY_APPLIED_SUCC_COST_5S_LESS)
            } else {
                reporter?.onReport(KEY_APPLIED_FAIL_COST_5S_LESS)
            }
        } else if (cost <= 10 * 1000) {
            if (success) {
                reporter?.onReport(KEY_APPLIED_SUCC_COST_10S_LESS)
            } else {
                reporter?.onReport(KEY_APPLIED_FAIL_COST_10S_LESS)
            }
        } else if (cost <= 30 * 1000) {
            if (success) {
                reporter?.onReport(KEY_APPLIED_SUCC_COST_30S_LESS)
            } else {
                reporter?.onReport(KEY_APPLIED_FAIL_COST_30S_LESS)
            }
        } else if (cost <= 60 * 1000) {
            if (success) {
                reporter?.onReport(KEY_APPLIED_SUCC_COST_60S_LESS)
            } else {
                reporter?.onReport(KEY_APPLIED_FAIL_COST_60S_LESS)
            }
        } else {
            if (success) {
                reporter?.onReport(KEY_APPLIED_SUCC_COST_OTHER)
            } else {
                reporter?.onReport(KEY_APPLIED_FAIL_COST_OTHER)
            }
        }
    }

    fun onApplyPackageCheckFail(errorCode: Int) {
        TinkerLog.i(TAG(), "hp_report package check failed, error = %d", errorCode)
        when (errorCode) {
            ERROR_PACKAGE_CHECK_SIGNATURE_FAIL -> reporter?.onReport(KEY_APPLIED_PACKAGE_CHECK_SIGNATURE)
            // "assets/dex_meta.txt"信息损坏
            ERROR_PACKAGE_CHECK_DEX_META_CORRUPTED -> reporter?.onReport(KEY_APPLIED_PACKAGE_CHECK_DEX_META)
            // "assets/so_meta.txt"信息损坏
            ERROR_PACKAGE_CHECK_LIB_META_CORRUPTED -> reporter?.onReport(KEY_APPLIED_PACKAGE_CHECK_LIB_META)
            // 找不到补丁中"assets/package_meta.txt"中的TINKER_ID
            ERROR_PACKAGE_CHECK_PATCH_TINKER_ID_NOT_FOUND -> reporter?.onReport(KEY_APPLIED_PACKAGE_CHECK_PATCH_TINKER_ID_NOT_FOUND)
            ERROR_PACKAGE_CHECK_APK_TINKER_ID_NOT_FOUND -> reporter?.onReport(KEY_APPLIED_PACKAGE_CHECK_APK_TINKER_ID_NOT_FOUND)
            ERROR_PACKAGE_CHECK_TINKER_ID_NOT_EQUAL -> reporter?.onReport(KEY_APPLIED_PACKAGE_CHECK_TINKER_ID_NOT_EQUAL)
            ERROR_PACKAGE_CHECK_PACKAGE_META_NOT_FOUND -> reporter?.onReport(KEY_APPLIED_PACKAGE_CHECK_META_NOT_FOUND)
            ERROR_PACKAGE_CHECK_RESOURCE_META_CORRUPTED -> reporter?.onReport(KEY_APPLIED_PACKAGE_CHECK_RES_META)
            ERROR_PACKAGE_CHECK_TINKERFLAG_NOT_SUPPORT -> reporter?.onReport(KEY_APPLIED_PACKAGE_CHECK_TINKER_FLAG_NOT_SUPPORT)
        }
    }

    fun onApplyCrash(throwable: Throwable?) = reporter?.onReport(KEY_APPLIED_EXCEPTION)

    fun onFastCrashProtect(message: String? = null) = reporter?.onReport(KEY_CRASH_FAST_PROTECT, message)

    fun onXposedCrash(message: String? = null) {
        reporter?.onReport(if (ShareTinkerInternals.isVmArt()) KEY_CRASH_CAUSE_XPOSED_ART else KEY_CRASH_CAUSE_XPOSED_DALVIK, message)
    }

    fun onReportRetryPatch() = reporter?.onReport(KEY_APPLY_WITH_RETRY, "KEY_APPLY_WITH_RETRY")
}
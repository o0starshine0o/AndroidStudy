package com.abelhu.androidstudy.reporter

import android.content.Context
import android.os.Looper
import com.tencent.tinker.lib.reporter.DefaultLoadReporter
import com.tencent.tinker.lib.util.UpgradePatchRetry
import com.tencent.tinker.loader.shareutil.ShareConstants
import java.io.File

/**
 * optional, you can just use DefaultLoadReporter
 */
class TinkerLoadReporter(context: Context?) : DefaultLoadReporter(context) {
    override fun onLoadPatchListenerReceiveFail(patchFile: File, errorCode: Int) {
        super.onLoadPatchListenerReceiveFail(patchFile, errorCode)
        TinkerReport.onTryApplyFail(errorCode)
    }

    override fun onLoadResult(patchDirectory: File, loadCode: Int, cost: Long) {
        super.onLoadResult(patchDirectory, loadCode, cost)
        when (loadCode) {
            ShareConstants.ERROR_LOAD_OK -> TinkerReport.onLoaded(cost)
        }
        Looper.myQueue().addIdleHandler {
            if (UpgradePatchRetry.getInstance(context).onPatchRetryLoad()) {
                TinkerReport.onReportRetryPatch()
            }
            false
        }
    }

    override fun onLoadException(e: Throwable, errorCode: Int) {
        super.onLoadException(e, errorCode)
        TinkerReport.onLoadException(e, errorCode)
    }

    override fun onLoadFileMd5Mismatch(file: File, fileType: Int) {
        super.onLoadFileMd5Mismatch(file, fileType)
        TinkerReport.onLoadFileMisMatch(fileType)
    }

    /**
     * try to recover patch oat file
     *
     * @param file
     * @param fileType
     * @param isDirectory
     */
    override fun onLoadFileNotFound(file: File, fileType: Int, isDirectory: Boolean) {
        super.onLoadFileNotFound(file, fileType, isDirectory)
        TinkerReport.onLoadFileNotFound(fileType)
    }

    override fun onLoadPackageCheckFail(patchFile: File, errorCode: Int) {
        super.onLoadPackageCheckFail(patchFile, errorCode)
        TinkerReport.onLoadPackageCheckFail(errorCode)
    }

    override fun onLoadPatchInfoCorrupted(oldVersion: String, newVersion: String, patchInfoFile: File) {
        super.onLoadPatchInfoCorrupted(oldVersion, newVersion, patchInfoFile)
        TinkerReport.onLoadInfoCorrupted()
    }

    override fun onLoadInterpret(type: Int, e: Throwable) {
        super.onLoadInterpret(type, e)
        TinkerReport.onLoadInterpretReport(type, e)
    }
}

package com.abelhu.androidstudy.reporter

import android.content.Context
import android.content.Intent
import com.tencent.tinker.lib.reporter.DefaultPatchReporter
import com.tencent.tinker.loader.shareutil.SharePatchInfo
import java.io.File


/**
 * optional, you can just use DefaultPatchReporter
 */
class ThinkPatchReporter(context: Context?) : DefaultPatchReporter(context) {
    override fun onPatchServiceStart(intent: Intent) {
        super.onPatchServiceStart(intent)
        TinkerReport.onApplyPatchServiceStart()
    }

    override fun onPatchDexOptFail(patchFile: File, dexFiles: List<File>, t: Throwable) {
        super.onPatchDexOptFail(patchFile, dexFiles, t)
        TinkerReport.onApplyDexOptFail(t)
    }

    override fun onPatchException(patchFile: File, e: Throwable) {
        super.onPatchException(patchFile, e)
        TinkerReport.onApplyCrash(e)
    }

    override fun onPatchInfoCorrupted(patchFile: File, oldVersion: String, newVersion: String) {
        super.onPatchInfoCorrupted(patchFile, oldVersion, newVersion)
        TinkerReport.onApplyInfoCorrupted()
    }

    override fun onPatchPackageCheckFail(patchFile: File, errorCode: Int) {
        super.onPatchPackageCheckFail(patchFile, errorCode)
        TinkerReport.onApplyPackageCheckFail(errorCode)
    }

    override fun onPatchResult(patchFile: File, success: Boolean, cost: Long) {
        super.onPatchResult(patchFile, success, cost)
        TinkerReport.onApplied(cost, success)
    }

    override fun onPatchTypeExtractFail(patchFile: File, extractTo: File, filename: String, fileType: Int) {
        super.onPatchTypeExtractFail(patchFile, extractTo, filename, fileType)
        TinkerReport.onApplyExtractFail(fileType)
    }

    override fun onPatchVersionCheckFail(patchFile: File, oldPatchInfo: SharePatchInfo, patchFileVersion: String) {
        super.onPatchVersionCheckFail(patchFile, oldPatchInfo, patchFileVersion)
        TinkerReport.onApplyVersionCheckFail()
    }
}
package com.abelhu.androidstudy.tinker

import android.content.Context
import android.content.Intent
import com.tencent.tinker.lib.reporter.DefaultPatchReporter
import com.tencent.tinker.loader.shareutil.SharePatchInfo
import java.io.File


/**
 * optional, you can just use DefaultPatchReporter
 */
class TinkerPatchReporter(context: Context?) : DefaultPatchReporter(context) {
    /**
     * 这个是Patch进程启动时的回调，我们可以在这里进行一个统计的工作。
     */
    override fun onPatchServiceStart(intent: Intent) {
        super.onPatchServiceStart(intent)
        TinkerReportHelper.onApplyPatchServiceStart()
    }

    /**
     * 对合成的dex文件提前进行dex opt时出现异常，默认我们会删除临时文件。
     */
    override fun onPatchDexOptFail(patchFile: File, dexFiles: List<File>, t: Throwable) {
        super.onPatchDexOptFail(patchFile, dexFiles, t)
        TinkerReportHelper.onApplyDexOptFail(patchFile, dexFiles, t)
    }

    /**
     * 在补丁合成过程捕捉到异常，十分希望你可以把错误信息反馈给我们。默认我们会删除临时文件，并且将tinkerFlag设为不可用。
     */
    override fun onPatchException(patchFile: File, e: Throwable) {
        super.onPatchException(patchFile, e)
        TinkerReportHelper.onApplyCrash(patchFile, e)
    }

    /**
     * patch.info是用来管理补丁包版本的文件，这是在更新info文件时发生损坏的回调。默认我们会卸载补丁包，因为此时我们已经无法恢复了。
     */
    override fun onPatchInfoCorrupted(patchFile: File, oldVersion: String, newVersion: String) {
        super.onPatchInfoCorrupted(patchFile, oldVersion, newVersion)
        TinkerReportHelper.onApplyInfoCorrupted(patchFile, oldVersion, newVersion)
    }

    /**
     * 补丁合成过程对输入补丁包的检查失败，这里可以通过错误码区分，例如签名校验失败、tinkerId不一致等原因。默认我们会删除临时文件。
     */
    override fun onPatchPackageCheckFail(patchFile: File, errorCode: Int) {
        super.onPatchPackageCheckFail(patchFile, errorCode)
        TinkerReportHelper.onApplyPackageCheckFail(patchFile, errorCode)
    }

    /**
     * 这个是无论补丁合成失败或者成功都会回调的接口，它返回了本次合成的类型，时间以及结果等。
     * 默认我们只是简单的输出这个信息，你可以在这里加上监控上报逻辑。
     */
    override fun onPatchResult(patchFile: File, success: Boolean, cost: Long) {
        super.onPatchResult(patchFile, success, cost)
        TinkerReportHelper.onApplied(patchFile, success, cost)
    }

    /**
     * 从补丁包与原始安装包中合成某种类型的文件出现错误，默认我们会删除临时文件。
     */
    override fun onPatchTypeExtractFail(patchFile: File, extractTo: File, filename: String, fileType: Int) {
        super.onPatchTypeExtractFail(patchFile, extractTo, filename, fileType)
        TinkerReportHelper.onApplyExtractFail(patchFile, extractTo, filename, fileType)
    }

    /**
     * 对patch.info的校验版本合法性校验。若校验失败，默认我们会删除临时文件。
     */
    override fun onPatchVersionCheckFail(patchFile: File, oldPatchInfo: SharePatchInfo, patchFileVersion: String) {
        super.onPatchVersionCheckFail(patchFile, oldPatchInfo, patchFileVersion)
        TinkerReportHelper.onApplyVersionCheckFail(patchFile, oldPatchInfo, patchFileVersion)
    }
}
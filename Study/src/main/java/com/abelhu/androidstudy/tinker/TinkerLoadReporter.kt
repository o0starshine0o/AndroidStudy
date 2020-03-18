package com.abelhu.androidstudy.tinker

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
    /**
     * 所有的补丁合成请求都需要先通过PatchListener的检查过滤。
     * 这次检查不通过的回调，它运行在发起请求的进程。默认我们只是打印日志
     */
    override fun onLoadPatchListenerReceiveFail(patchFile: File, errorCode: Int) {
        super.onLoadPatchListenerReceiveFail(patchFile, errorCode)
        TinkerReport.onTryApplyFail(errorCode)
    }

    /**
     * 这个是无论加载失败或者成功都会回调的接口，它返回了本次加载所用的时间、返回码等信息。
     * 默认我们只是简单的输出这个信息，你可以在这里加上监控上报逻辑。
     */
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

    /**
     * 在加载过程捕捉到异常，十分希望你可以把错误信息反馈给我们。默认我们会直接卸载补丁包
     */
    override fun onLoadException(e: Throwable, errorCode: Int) {
        super.onLoadException(e, errorCode)
        TinkerReport.onLoadException(e, errorCode)
    }

    /**
     * 部分文件的md5与meta中定义的不一致。默认我们为了安全考虑，依然会清空补丁。
     */
    override fun onLoadFileMd5Mismatch(file: File, fileType: Int) {
        super.onLoadFileMd5Mismatch(file, fileType)
        TinkerReport.onLoadFileMisMatch(fileType)
    }

    /**
     * 在加载过程中，发现部分文件丢失的回调。默认若是dex，dex优化文件或者lib文件丢失，我们将尝试从补丁包去修复这些丢失的文件。
     * 若补丁包或者版本文件丢失，将卸载补丁包。
     * 对于onLoadPatchVersionChanged与onLoadFileNotFound的复写要较为谨慎，
     * 因为版本升级杀掉其他进程与文件丢失发起恢复任务，都是我认为比较重要的操作
     */
    override fun onLoadFileNotFound(file: File, fileType: Int, isDirectory: Boolean) {
        super.onLoadFileNotFound(file, fileType, isDirectory)
        TinkerReport.onLoadFileNotFound(fileType)
    }

    /**
     * 加载过程补丁包的检查失败，这里可以通过错误码区分，例如签名校验失败、tinkerId不一致等原因。默认我们将会卸载补丁包
     */
    override fun onLoadPackageCheckFail(patchFile: File, errorCode: Int) {
        super.onLoadPackageCheckFail(patchFile, errorCode)
        TinkerReport.onLoadPackageCheckFail(errorCode)
    }

    /**
     * patch.info是用来管理补丁包版本的文件，这是info文件损坏的回调。默认我们会卸载补丁包，因为此时我们已经无法恢复了。
     */
    override fun onLoadPatchInfoCorrupted(oldVersion: String, newVersion: String, patchInfoFile: File) {
        super.onLoadPatchInfoCorrupted(oldVersion, newVersion, patchInfoFile)
        TinkerReport.onLoadInfoCorrupted()
    }

    /**
     * 系统OTA后，为了加快补丁的执行，我们会采用解释模式来执行补丁。
     */
    override fun onLoadInterpret(type: Int, e: Throwable) {
        super.onLoadInterpret(type, e)
        TinkerReport.onLoadInterpretReport(type, e)
    }

}

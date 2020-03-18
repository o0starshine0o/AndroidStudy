package com.abelhu.androidstudy.tinker

import android.os.Handler
import android.os.Looper
import android.os.Process
import android.widget.Toast
import com.abelhu.androidstudy.utils.AppUtils
import com.qicode.extension.TAG
import com.tencent.tinker.lib.service.DefaultTinkerResultService
import com.tencent.tinker.lib.service.PatchResult
import com.tencent.tinker.lib.util.TinkerLog
import com.tencent.tinker.lib.util.TinkerServiceInternals
import java.io.File


/**
 * optional, you can just use DefaultTinkerResultService
 * we can restart process when we are at background or screen off
 */
class TinkerResultService : DefaultTinkerResultService() {
    override fun onPatchResult(result: PatchResult?) {
        if (result == null) {
            TinkerLog.e(TAG(), "SampleResultService received null result!!!!")
            return
        }
        TinkerLog.i(TAG(), "SampleResultService receive result: %s", result.toString())
        //first, we want to kill the recover process
        TinkerServiceInternals.killTinkerPatchServiceProcess(applicationContext)
        Handler(Looper.getMainLooper()).post {
            if (result.isSuccess) Toast.makeText(applicationContext, "patch success, please restart process", Toast.LENGTH_LONG).show()
            else Toast.makeText(applicationContext, "patch fail, please check reason", Toast.LENGTH_LONG).show()
        }
        // is success and newPatch, it is nice to delete the raw file, and restart at once for old patch, you can't delete the patch file
        if (result.isSuccess) {
            deleteRawPatchFile(File(result.rawPatchFilePath))
            //not like TinkerResultService, I want to restart just when I am at background!
            //if you have not install tinker this moment, you can use TinkerApplicationHelper api
            if (checkIfNeedKill(result)) {
                if (AppUtils.background) {
                    TinkerLog.i(TAG(), "it is in background, just restart process")
                    restartProcess()
                } else {
                    //we can wait process at background, such as onAppBackground or we can restart when the screen off
                    TinkerLog.i(TAG(), "tinker wait screen to restart process")
                    AppUtils.ScreenState(applicationContext) { restartProcess() }
                }
            } else {
                TinkerLog.i(TAG(), "I have already install the newly patch version!")
            }
        }
    }

    /**
     * you can restart your process through service or broadcast
     */
    private fun restartProcess() {
        TinkerLog.i(TAG(), "app is background now, i can kill quietly")
        //you can send service or broadcast intent to restart your process
        Process.killProcess(Process.myPid())
    }
}
package com.abelhu.androidstudy.reporter

import android.app.ActivityManager
import android.content.Context
import android.os.Environment
import android.os.StatFs
import com.abelhu.androidstudy.BuildConfig
import com.abelhu.androidstudy.crash.UncaughtExceptionHandler
import com.abelhu.androidstudy.utils.TinkerManager.ERROR_PATCH_CONDITION_NOT_SATISFIED
import com.abelhu.androidstudy.utils.TinkerManager.ERROR_PATCH_CRASH_LIMIT
import com.abelhu.androidstudy.utils.TinkerManager.ERROR_PATCH_GOOGLE_PLAY_CHANNEL
import com.abelhu.androidstudy.utils.TinkerManager.ERROR_PATCH_MEMORY_LIMIT
import com.abelhu.androidstudy.utils.TinkerManager.ERROR_PATCH_ROM_SPACE
import com.abelhu.androidstudy.utils.TinkerManager.MIN_MEMORY_HEAP_SIZE
import com.abelhu.androidstudy.utils.TinkerManager.PLATFORM
import com.qicode.extension.TAG
import com.tencent.tinker.lib.listener.DefaultPatchListener
import com.tencent.tinker.lib.util.TinkerLog
import com.tencent.tinker.loader.shareutil.ShareConstants
import com.tencent.tinker.loader.shareutil.SharePatchFileUtil
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals
import java.io.File


/**
 * optional, you can just use DefaultPatchListener
 * we can check whatever you want whether we actually send a patch request
 * such as we can check rom space or apk channel
 */
class TinkerPatchListener(context: Context) : DefaultPatchListener(context) {
    private val maxMemory = (context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).memoryClass

    companion object {
        const val NEW_PATCH_RESTRICTION_SPACE_SIZE_MIN = 60 * 1024 * 1024.toLong()
    }

    /**
     * because we use the defaultCheckPatchReceived method
     * the error code define by myself should after `ShareConstants.ERROR_RECOVER_IN_SERVICE
     *
     * path
     * newPatch
    ` *
     */
    @Suppress("DEPRECATION")
    public override fun patchCheck(path: String, patchMd5: String): Int {
        val patchFile = File(path)
        TinkerLog.i(TAG(), "receive a patch file: %s, file size:%d", path, SharePatchFileUtil.getFileOrDirectorySize(patchFile))
        var returnCode = super.patchCheck(path, patchMd5)
        if (returnCode == ShareConstants.ERROR_PATCH_OK) returnCode = checkForPatchRecover(NEW_PATCH_RESTRICTION_SPACE_SIZE_MIN, maxMemory)
        if (returnCode == ShareConstants.ERROR_PATCH_OK) {
            val sp = context.getSharedPreferences(ShareConstants.TINKER_SHARE_PREFERENCE_CONFIG, Context.MODE_MULTI_PROCESS)
            //optional, only disable this patch file with md5
            val fastCrashCount = sp.getInt(patchMd5, 0)
            if (fastCrashCount >= UncaughtExceptionHandler.MAX_CRASH_COUNT) {
                returnCode = ERROR_PATCH_CRASH_LIMIT
            }
        }
        // Warning, it is just a sample case, you don't need to copy all of these Interception some of the request
        if (returnCode == ShareConstants.ERROR_PATCH_OK) {
            val properties = ShareTinkerInternals.fastGetPatchPackageMeta(patchFile)
            if (properties == null) {
                returnCode = ERROR_PATCH_CONDITION_NOT_SATISFIED
            } else {
                val platform = properties.getProperty(PLATFORM)
                TinkerLog.i(TAG(), "get platform:$platform")
                // check patch platform require
                if (platform == null || platform != BuildConfig.PLATFORM) {
                    returnCode = ERROR_PATCH_CONDITION_NOT_SATISFIED
                }
            }
        }
        TinkerReport.onTryApply(returnCode == ShareConstants.ERROR_PATCH_OK)
        return returnCode
    }

    @Suppress("SameParameterValue")
    private fun checkForPatchRecover(roomSize: Long, maxMemory: Int): Int {
        if (isGooglePlay()) {
            return ERROR_PATCH_GOOGLE_PLAY_CHANNEL
        }
        if (maxMemory < MIN_MEMORY_HEAP_SIZE) {
            return ERROR_PATCH_MEMORY_LIMIT
        }
        //or you can mention user to clean their rom space!
        return if (!checkRomSpaceEnough(roomSize)) {
            ERROR_PATCH_ROM_SPACE
        } else ShareConstants.ERROR_PATCH_OK
    }

    private fun isGooglePlay() = false

    @Suppress("DEPRECATION")
    private fun checkRomSpaceEnough(limitSize: Long): Boolean {
        var allSize: Long
        var availableSize: Long = 0
        try {
            val data: File = Environment.getDataDirectory()
            val sf = StatFs(data.path)
            availableSize = sf.availableBlocks.toLong() * sf.blockSize.toLong()
            allSize = sf.blockCount.toLong() * sf.blockSize.toLong()
        } catch (e: Exception) {
            allSize = 0
        }
        return allSize != 0L && availableSize > limitSize
    }
}
package com.abelhu.androidstudy.tinker

import android.util.Log
import com.qicode.extension.TAG

class TinkerReporter : TinkerReportHelper.Reporter {
    override fun onReport(key: Int, message: String?) {
        // TODO，在这里实现上报服务器的功能
        Log.i(TAG(), "[$key]:$message")
    }

    override fun onReport(message: String?) {
        // TODO，在这里实现上报服务器的功能
        Log.i(TAG(), "$message")
    }
}
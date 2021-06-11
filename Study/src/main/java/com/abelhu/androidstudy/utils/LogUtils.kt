package com.abelhu.androidstudy.utils

import android.util.Log
import com.abelhu.androidstudy.BuildConfig

class L {
    companion object {
        fun d(message: String, tag: String = BuildConfig.APPLICATION_ID) = Log.d(tag, message)
    }

}
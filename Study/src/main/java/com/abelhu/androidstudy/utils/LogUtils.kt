package com.abelhu.androidstudy.utils

import android.util.Log
import com.abelhu.androidstudy.BuildConfig

class L {
    companion object {
        fun d(message: String, tag: String = BuildConfig.APPLICATION_ID) = Log.d(tag, message)
    }

}

/**
 * 开发、测试阶段各级别日志均可输出
 * Release版本只输出Info、Warning、Error级别；
 * 另外Release版本会将Info及以上级别日志写入XLog,所以不可过滤；
 * */

inline fun <T> T.logV(tag: String? = BuildConfig.APPLICATION_ID, block: T.() -> String): T {
    return apply { if (BuildConfig.DEBUG) Log.v(tag, block()) }
}

inline fun <T> T.logD(tag: String? = BuildConfig.APPLICATION_ID, block: T.() -> String): T {
    return apply { if (BuildConfig.DEBUG) Log.d(tag, block()) }
}

inline fun <T> T.logI(tag: String? = BuildConfig.APPLICATION_ID, block: T.() -> String): T {
    return apply { if (BuildConfig.DEBUG) Log.i(tag, block()) }
}

inline fun <T> T.logW(tag: String? = BuildConfig.APPLICATION_ID, block: T.() -> String): T {
    return apply { if (BuildConfig.DEBUG) Log.w(tag, block()) }
}

inline fun <T> T.logE(tag: String? = BuildConfig.APPLICATION_ID, block: T.() -> String): T {
    return apply { if (BuildConfig.DEBUG) Log.e(tag, block()) }
}
package com.abelhu.leet.utils

const val TAG = "LEET"

class L {
    companion object {
        fun d(message: String, tag: String = TAG) = println("$tag, $message")
    }

}

/**
 * 开发、测试阶段各级别日志均可输出
 * Release版本只输出Info、Warning、Error级别；
 * 另外Release版本会将Info及以上级别日志写入XLog,所以不可过滤；
 * */

inline fun <T> T.log(block: (t: T) -> String): T {
    return apply { println("$TAG, ${block.invoke(this)}") }
}
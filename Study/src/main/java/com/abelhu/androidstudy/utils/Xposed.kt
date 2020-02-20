package com.abelhu.androidstudy.utils

object Xposed {
    fun isXposedExists(throwable: Throwable): Boolean {
        for (stackTrace in throwable.stackTrace) if (stackTrace.className.contains("de.robv.android.xposed.XposedBridge")) return true
        return false
    }
}
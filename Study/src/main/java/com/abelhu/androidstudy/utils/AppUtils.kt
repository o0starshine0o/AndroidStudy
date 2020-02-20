package com.abelhu.androidstudy.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.qicode.extension.TAG
import com.tencent.tinker.lib.util.TinkerLog

typealias OnScreenOff = () -> Unit

class AppUtils {
    companion object {
        var background = false
    }

    class ScreenState(context: Context, onScreenOffInterface: OnScreenOff?) {
        init {
            context.registerReceiver(object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent?) {
                    intent?.apply {
                        TinkerLog.i(TAG(), "ScreenReceiver action [%s] ", action)
                        if (Intent.ACTION_SCREEN_OFF == action) onScreenOffInterface?.invoke()
                    }
                    context.unregisterReceiver(this)
                }
            }, IntentFilter().apply { addAction(Intent.ACTION_SCREEN_OFF) })
        }
    }
}
package com.abelhu.androidstudy.application

import android.view.Choreographer
import com.abelhu.androidstudy.utils.logE

class FPSFrameCallback(private var lastFrameTimeNanos: Long = System.nanoTime()): Choreographer.FrameCallback {
    companion object{
        private const val TAG = "FPSFrameCallback"

        private const val FRAME_INTERVAL_NANOS = 1_000_000_000L / 60
        private const val SKIP_FRAME_LIMIT = 30
    }

    override fun doFrame(frameTimeNanos: Long) {
        // 计算2帧之间的时差
        val jitterNanos = frameTimeNanos - lastFrameTimeNanos
        if (jitterNanos >= FRAME_INTERVAL_NANOS) {
            val skipFrames = jitterNanos / FRAME_INTERVAL_NANOS
            if (skipFrames >= SKIP_FRAME_LIMIT) {
                logE(TAG) { "skip $skipFrames" }
            }
        }
        // 记录本次页面刷新的时间
        lastFrameTimeNanos = frameTimeNanos
        // 注册下一帧的回调
        Choreographer.getInstance().postFrameCallback(this)
    }
}
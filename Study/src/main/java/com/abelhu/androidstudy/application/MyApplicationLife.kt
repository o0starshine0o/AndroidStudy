package com.abelhu.androidstudy.application

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.abelhu.androidstudy.tinker.TinkerUtils
import com.abelhu.androidstudy.ui.main.MainActivity
import com.abelhu.androidstudy.ui.screen.ScreenActivity

class MyApplicationLife : Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStarted(activity: Activity) {
        when (activity) {
            is MainActivity -> TinkerUtils.background = false
            is ScreenActivity -> TinkerUtils.background = false
        }
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityStopped(activity: Activity) {
        when (activity) {
            is MainActivity -> TinkerUtils.background = true
            is ScreenActivity -> TinkerUtils.background = true
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityResumed(activity: Activity) {
    }
}
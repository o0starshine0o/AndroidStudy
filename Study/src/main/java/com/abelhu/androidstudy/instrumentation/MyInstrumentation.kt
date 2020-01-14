package com.abelhu.androidstudy.instrumentation

import android.app.Activity
import android.app.Application
import android.app.Instrumentation
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import com.abelhu.androidstudy.extension.tag

class MyInstrumentation : Instrumentation() {

    override fun callActivityOnPostCreate(activity: Activity, savedInstanceState: Bundle?) {
        super.callActivityOnPostCreate(activity, savedInstanceState)
        Log.d(tag(), "callActivityOnPostCreate")
    }

    override fun callActivityOnPostCreate(activity: Activity, savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.callActivityOnPostCreate(activity, savedInstanceState, persistentState)
        Log.d(tag(), "callActivityOnPostCreate")
    }

    override fun callActivityOnRestoreInstanceState(activity: Activity, savedInstanceState: Bundle) {
        super.callActivityOnRestoreInstanceState(activity, savedInstanceState)
        Log.d(tag(), "callActivityOnRestoreInstanceState")
    }

    override fun callActivityOnRestoreInstanceState(activity: Activity, savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.callActivityOnRestoreInstanceState(activity, savedInstanceState, persistentState)
        Log.d(tag(), "callActivityOnRestoreInstanceState")
    }

    override fun callActivityOnStart(activity: Activity?) {
        super.callActivityOnStart(activity)
        Log.d(tag(), "callActivityOnStart")
    }

    override fun callActivityOnRestart(activity: Activity?) {
        super.callActivityOnRestart(activity)
        Log.d(tag(), "callActivityOnRestart")
    }

    override fun callApplicationOnCreate(app: Application?) {
        super.callApplicationOnCreate(app)
        Log.d(tag(), "callApplicationOnCreate")
    }

    override fun callActivityOnNewIntent(activity: Activity?, intent: Intent?) {
        super.callActivityOnNewIntent(activity, intent)
        Log.d(tag(), "callActivityOnNewIntent")
    }

    override fun callActivityOnStop(activity: Activity?) {
        super.callActivityOnStop(activity)
        Log.d(tag(), "callActivityOnStop")
    }

    override fun callActivityOnCreate(activity: Activity?, icicle: Bundle?) {
        super.callActivityOnCreate(activity, icicle)
        Log.d(tag(), "callActivityOnCreate")
    }

    override fun callActivityOnCreate(activity: Activity?, icicle: Bundle?, persistentState: PersistableBundle?) {
        super.callActivityOnCreate(activity, icicle, persistentState)
        Log.d(tag(), "callActivityOnCreate")
    }

    override fun callActivityOnSaveInstanceState(activity: Activity, outState: Bundle) {
        super.callActivityOnSaveInstanceState(activity, outState)
        Log.d(tag(), "callActivityOnSaveInstanceState")
    }

    override fun callActivityOnSaveInstanceState(activity: Activity, outState: Bundle, outPersistentState: PersistableBundle) {
        super.callActivityOnSaveInstanceState(activity, outState, outPersistentState)
        Log.d(tag(), "callActivityOnSaveInstanceState")
    }

    override fun callActivityOnUserLeaving(activity: Activity?) {
        super.callActivityOnUserLeaving(activity)
        Log.d(tag(), "callActivityOnUserLeaving")
    }

    override fun callActivityOnDestroy(activity: Activity?) {
        super.callActivityOnDestroy(activity)
        Log.d(tag(), "callActivityOnDestroy")
    }

    override fun callActivityOnPause(activity: Activity?) {
        super.callActivityOnPause(activity)
        Log.d(tag(), "callActivityOnPause")
    }

    override fun callActivityOnResume(activity: Activity?) {
        super.callActivityOnResume(activity)
        Log.d(tag(), "callActivityOnResume")
    }
}
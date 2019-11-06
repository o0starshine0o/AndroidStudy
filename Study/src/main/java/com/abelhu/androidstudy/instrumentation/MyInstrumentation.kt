package com.abelhu.androidstudy.instrumentation

import android.app.Activity
import android.app.Application
import android.app.Instrumentation
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log

class MyInstrumentation : Instrumentation() {
    companion object {
        private val Tag = MyInstrumentation::class.simpleName
    }

    override fun callActivityOnPostCreate(activity: Activity, savedInstanceState: Bundle?) {
        super.callActivityOnPostCreate(activity, savedInstanceState)
        Log.d(Tag, "callActivityOnPostCreate")
    }

    override fun callActivityOnPostCreate(activity: Activity, savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.callActivityOnPostCreate(activity, savedInstanceState, persistentState)
        Log.d(Tag, "callActivityOnPostCreate")
    }

    override fun callActivityOnRestoreInstanceState(activity: Activity, savedInstanceState: Bundle) {
        super.callActivityOnRestoreInstanceState(activity, savedInstanceState)
        Log.d(Tag, "callActivityOnRestoreInstanceState")
    }

    override fun callActivityOnRestoreInstanceState(activity: Activity, savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.callActivityOnRestoreInstanceState(activity, savedInstanceState, persistentState)
        Log.d(Tag, "callActivityOnRestoreInstanceState")
    }

    override fun callActivityOnStart(activity: Activity?) {
        super.callActivityOnStart(activity)
        Log.d(Tag, "callActivityOnStart")
    }

    override fun callActivityOnRestart(activity: Activity?) {
        super.callActivityOnRestart(activity)
        Log.d(Tag, "callActivityOnRestart")
    }

    override fun callApplicationOnCreate(app: Application?) {
        super.callApplicationOnCreate(app)
        Log.d(Tag, "callApplicationOnCreate")
    }

    override fun callActivityOnNewIntent(activity: Activity?, intent: Intent?) {
        super.callActivityOnNewIntent(activity, intent)
        Log.d(Tag, "callActivityOnNewIntent")
    }

    override fun callActivityOnStop(activity: Activity?) {
        super.callActivityOnStop(activity)
        Log.d(Tag, "callActivityOnStop")
    }

    override fun callActivityOnCreate(activity: Activity?, icicle: Bundle?) {
        super.callActivityOnCreate(activity, icicle)
        Log.d(Tag, "callActivityOnCreate")
    }

    override fun callActivityOnCreate(activity: Activity?, icicle: Bundle?, persistentState: PersistableBundle?) {
        super.callActivityOnCreate(activity, icicle, persistentState)
        Log.d(Tag, "callActivityOnCreate")
    }

    override fun callActivityOnSaveInstanceState(activity: Activity, outState: Bundle) {
        super.callActivityOnSaveInstanceState(activity, outState)
        Log.d(Tag, "callActivityOnSaveInstanceState")
    }

    override fun callActivityOnSaveInstanceState(activity: Activity, outState: Bundle, outPersistentState: PersistableBundle) {
        super.callActivityOnSaveInstanceState(activity, outState, outPersistentState)
        Log.d(Tag, "callActivityOnSaveInstanceState")
    }

    override fun callActivityOnUserLeaving(activity: Activity?) {
        super.callActivityOnUserLeaving(activity)
        Log.d(Tag, "callActivityOnUserLeaving")
    }

    override fun callActivityOnDestroy(activity: Activity?) {
        super.callActivityOnDestroy(activity)
        Log.d(Tag, "callActivityOnDestroy")
    }

    override fun callActivityOnPause(activity: Activity?) {
        super.callActivityOnPause(activity)
        Log.d(Tag, "callActivityOnPause")
    }

    override fun callActivityOnResume(activity: Activity?) {
        super.callActivityOnResume(activity)
        Log.d(Tag, "callActivityOnResume")
    }
}
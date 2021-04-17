package com.abelhu.androidstudy.application

import android.app.Application

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(MyApplicationLife.instance)
    }
}
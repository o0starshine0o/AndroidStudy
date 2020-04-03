package com.abelhu.androidstudy.binder.service

import android.app.Service
import android.content.Intent

/**
 * 要初始化一个IBinder对象
 */
class MyBinderService : Service() {
    private lateinit var binder: MyBinder

    override fun onCreate() {
        super.onCreate()
        // 1、创建一个实现了IInterface接口的对象
        val face = MyInterface()
        // 2、创建一个实现了IBinder接口的对象
        binder = face.asBinder()
        // 3、把上面2个对象保存到Binder对象中的一个Map<String,IInterface>属性中，之后通过descriptor获取到IInterface
        binder.attachInterface(face, "19920320")
    }

    // 4、通过onBind函数，把binder注册到ServiceManager
    override fun onBind(intent: Intent?) = binder
}